package com.muedsa.tvbox.mjtt.service

import com.muedsa.tvbox.api.data.MediaCardRow
import com.muedsa.tvbox.api.service.IMediaSearchService
import com.muedsa.tvbox.mjtt.CardHeight
import com.muedsa.tvbox.mjtt.CardWidth
import com.muedsa.tvbox.tool.feignChrome
import org.jsoup.Jsoup
import java.net.CookieStore

class MediaSearchService(
    private val cookieStore: CookieStore
) : IMediaSearchService {

    override suspend fun searchMedias(query: String): MediaCardRow {
        val body = Jsoup.connect("${MJTTService.getSiteUrl()}/vod-search.html")
            .feignChrome(cookieStore = cookieStore)
            .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
            .data("wd", query)
            .post()
            .body()
        val ulEl = body.selectFirst(".container .row .z-pannel .z-pannel_bd .z-list")!!
        val rows = mutableListOf<MediaCardRow>()
        MJTTService.appendRowWithUl(rows = rows, rowTitle = "search list", ulEl = ulEl)
        return if (rows.size > 0) rows[0] else MediaCardRow(
            title = "search list",
            cardWidth = CardWidth,
            cardHeight = CardHeight,
            list = emptyList()
        )
    }
}