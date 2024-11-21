package com.muedsa.tvbox.mjtt.service


import com.muedsa.tvbox.api.data.MediaCatalogOption
import com.muedsa.tvbox.mjtt.TestPlugin
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MediaCatalogServiceTest {

    private val service = TestPlugin.provideMediaCatalogService()

    @Test
    fun getConfig_test() = runTest {
        val config = service.getConfig()
        check(config.initKey == "1")
        check(config.pageSize > 0)
        check(config.cardWidth > 0)
        check(config.cardHeight > 0)
        check(config.catalogOptions.isNotEmpty())
    }

    @Test
    fun catalog_test() = runTest {
        val config = service.getConfig()
        val options = MediaCatalogOption.getDefault(config.catalogOptions)
        val result =
            service.catalog(options = options, loadKey = config.initKey, loadSize = config.pageSize)
        check(result.list.isNotEmpty())
        result.list.forEach { card ->
            check(card.id.isNotEmpty())
            check(card.title.isNotEmpty())
            check(card.detailUrl.isNotEmpty())
            println("${card.id} ${card.title} ${card.detailUrl}")
            check(card.coverImageUrl.isNotBlank())
            println(card.coverImageUrl)
        }
        check(result.prevKey == null)
        check(result.nextKey == "2")
    }
}