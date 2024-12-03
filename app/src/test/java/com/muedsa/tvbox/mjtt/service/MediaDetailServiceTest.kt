package com.muedsa.tvbox.mjtt.service

import com.muedsa.tvbox.api.data.MediaCardType
import com.muedsa.tvbox.mjtt.TestPlugin
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MediaDetailServiceTest {

    private val service = TestPlugin.provideMediaDetailService()

    @Test
    fun getDetailData_test() = runTest {
        val media = TestPlugin.provideMainScreenService().getRowsData()[0].list[0]
        val detail = service.getDetailData(media.id, media.detailUrl)
        check(detail.id.isNotEmpty())
        println(detail.id)
        check(detail.title.isNotEmpty())
        println(detail.title)
        check(detail.detailUrl.isNotEmpty())
        println(detail.detailUrl)
        check(detail.backgroundImageUrl.isNotEmpty())
        println(detail.backgroundImageUrl)
        detail.favoritedMediaCard?.let { favoritedMediaCard ->
            check(favoritedMediaCard.id.isNotEmpty())
            println(favoritedMediaCard.id)
            check(favoritedMediaCard.title.isNotEmpty())
            println(favoritedMediaCard.title)
            check(favoritedMediaCard.detailUrl.isNotEmpty())
            println(favoritedMediaCard.detailUrl)
            check(favoritedMediaCard.cardWidth > 0)
            check(favoritedMediaCard.cardHeight > 0)
            println("${favoritedMediaCard.cardWidth}X${favoritedMediaCard.cardHeight}")
        }
        check(detail.playSourceList.isNotEmpty())
        detail.playSourceList.forEach { mediaPlaySource ->
            println("${mediaPlaySource.id}:${mediaPlaySource.name}")
            check(mediaPlaySource.id.isNotEmpty())
            check(mediaPlaySource.name.isNotEmpty())
            check(mediaPlaySource.episodeList.isNotEmpty())
            mediaPlaySource.episodeList.forEach {
                check(it.id.isNotEmpty())
                check(it.name.isNotEmpty())
                println("${it.name} -> ${it.id}")
            }
        }

        detail.rows.forEach { row ->
            check(row.title.isNotBlank())
            check(row.cardWidth > 0)
            check(row.cardHeight > 0)
            check(row.list.isNotEmpty())
            println("${row.title} ${row.cardWidth}X${row.cardType} ${row.cardType}")
            row.list.forEach {
                check(it.id.isNotEmpty())
                check(it.title.isNotEmpty())
                check(it.detailUrl.isNotEmpty())
                println("${it.id} ${it.title} ${it.detailUrl}")
                if (row.cardType != MediaCardType.NOT_IMAGE) {
                    check(it.coverImageUrl.isNotBlank())
                    println(it.coverImageUrl)
                } else {
                    check(it.backgroundColor > 0)
                    println(it.backgroundColor)
                }
            }
        }
    }

    @Test
    fun getEpisodePlayInfo_test() = runTest {
        val media = TestPlugin.provideMainScreenService().getRowsData()[0].list[0]
        val detail = service.getDetailData(media.id, media.detailUrl)
        check(detail.playSourceList.isNotEmpty())
        check(detail.playSourceList.flatMap { it.episodeList }.isNotEmpty())
        val mediaPlaySource = detail.playSourceList[0]
        val mediaEpisode = mediaPlaySource.episodeList[0]
        val playInfo = service.getEpisodePlayInfo(mediaPlaySource, mediaEpisode)
        check(playInfo.url.isNotEmpty())
        println(playInfo.url)
    }

}