package com.muedsa.tvbox.mjtt.service

import com.muedsa.tvbox.api.data.MediaCardRow
import com.muedsa.tvbox.api.service.IMediaSearchService
import com.muedsa.tvbox.mjtt.CardHeight
import com.muedsa.tvbox.mjtt.CardWidth
import com.muedsa.tvbox.tool.checkSuccess
import com.muedsa.tvbox.tool.feignChrome
import com.muedsa.tvbox.tool.parseHtml
import com.muedsa.tvbox.tool.post
import com.muedsa.tvbox.tool.toRequestBuild
import okhttp3.FormBody
import okhttp3.OkHttpClient

class MediaSearchService(
    private val mjttService: MJTTService,
    private val okHttpClient: OkHttpClient
) : IMediaSearchService {

    override suspend fun searchMedias(query: String): MediaCardRow {
        val body = "${mjttService.getSiteUrl()}/vod-search.html".toRequestBuild()
            .feignChrome()
            .post(
                body = FormBody.Builder()
                    .add("wd", query)
                    .build(),
                okHttpClient = okHttpClient
            )
            .checkSuccess()
            .parseHtml()
            .body()
        val ulEl = body.selectFirst(".container .row .z-pannel .z-pannel_bd .z-list")!!
        val rows = mutableListOf<MediaCardRow>()
        MJTTService.appendRowWithUl(rows = rows, rowTitle = "search list", ulEl = ulEl)
        return if (rows.isNotEmpty()) rows[0] else MediaCardRow(
            title = "search list",
            cardWidth = CardWidth,
            cardHeight = CardHeight,
            list = emptyList()
        )
    }
}