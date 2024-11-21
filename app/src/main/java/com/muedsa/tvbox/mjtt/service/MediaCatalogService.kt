package com.muedsa.tvbox.mjtt.service

import com.muedsa.tvbox.api.data.MediaCard
import com.muedsa.tvbox.api.data.MediaCatalogConfig
import com.muedsa.tvbox.api.data.MediaCatalogOption
import com.muedsa.tvbox.api.data.PagingResult
import com.muedsa.tvbox.api.service.IMediaCatalogService
import com.muedsa.tvbox.mjtt.CardHeight
import com.muedsa.tvbox.mjtt.CardWidth
import com.muedsa.tvbox.mjtt.CatalogOptions
import com.muedsa.tvbox.tool.checkSuccess
import com.muedsa.tvbox.tool.feignChrome
import com.muedsa.tvbox.tool.get
import com.muedsa.tvbox.tool.parseHtml
import com.muedsa.tvbox.tool.toRequestBuild
import okhttp3.OkHttpClient

class MediaCatalogService(
    private val mjttService: MJTTService,
    private val okHttpClient: OkHttpClient,
) : IMediaCatalogService {

    override suspend fun getConfig(): MediaCatalogConfig =
        MediaCatalogConfig(
            initKey = "1",
            pageSize = 30,
            cardWidth = CardWidth,
            cardHeight = CardHeight,
            catalogOptions = CatalogOptions
        )

    override suspend fun catalog(
        options: List<MediaCatalogOption>,
        loadKey: String,
        loadSize: Int
    ): PagingResult<MediaCard> {
        // /mhkh/index_1_289__2024__addtime_美国_1.html
        val page = loadKey.toInt()
        val category = options.findOptionFirstValue("category", defaultValue = "")
        val genre = options.findOptionFirstValue("genre", defaultValue = "")
        val region = options.findOptionFirstValue("region", defaultValue = "")
        val year = options.findOptionFirstValue("year", defaultValue = "")
        val order = options.findOptionFirstValue("order", defaultValue = "")
        val url =
            "https://www.mjtt5.net/$category/index_${page}_${genre}__${year}__${order}_${region}_1.html"
        val body = url.toRequestBuild()
            .feignChrome()
            .get(okHttpClient = okHttpClient)
            .checkSuccess()
            .parseHtml()
            .body()
        val cards = body.select(".container >.row .z-pannel .z-pannel_bd .z-list li").map { liEl ->
            val picEl = liEl.selectFirst(".z-list_pic")!!
            val title = liEl.selectFirst(".z-list_title")!!.text().trim()
            val status = liEl.selectFirst(".z-status")?.text()?.trim() ?: ""
            val type = liEl.selectFirst(".z-type")?.text()?.trim() ?: ""
            MediaCard(
                id = picEl.attr("href"),
                title = title,
                detailUrl = picEl.attr("href"),
                coverImageUrl = picEl.absUrl("data-original"),
                subTitle = "$status $type"
            )
        }
        val pageEmEls = body.select(".container >.row .z-pannel .z-page *[data]")
        return PagingResult(
            list = cards,
            prevKey = pageEmEls.find { it.text().trim() == "上一页" }?.attr("data")
                ?.removePrefix("p-")?.let { if (it == "0") null else it },
            nextKey = if (cards.isEmpty()) null else pageEmEls.find { it.text().trim() == "下一页" }
                ?.attr("data")?.removePrefix("p-")?.let { if (it == "0") null else it }
        )
    }

    companion object {
        fun List<MediaCatalogOption>.findOptionFirstValue(
            optionValue: String,
            defaultValue: String
        ): String {
            val option = find { it.value == optionValue }
            return if (option != null && option.items.isNotEmpty()) {
                option.items[0].value
            } else defaultValue
        }
    }
}