package com.muedsa.tvbox.mjtt.service

import com.muedsa.tvbox.mjtt.TestPlugin
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MediaSearchServiceTest {

    private val service = TestPlugin.provideMediaSearchService()

    @Test
    fun searchMedias_test() = runTest {
        val media = TestPlugin.provideMainScreenService().getRowsData()[0].list[0]
        val row = service.searchMedias(media.title)
        check(row.list.isNotEmpty())
        check(row.cardWidth > 0)
        check(row.cardHeight > 0)
        row.list.forEach {
            check(it.id.isNotEmpty())
            check(it.title.isNotEmpty())
            check(it.detailUrl.isNotEmpty())
            println("${it.id} ${it.title} ${it.detailUrl} ${it.coverImageUrl}")
        }
    }
}