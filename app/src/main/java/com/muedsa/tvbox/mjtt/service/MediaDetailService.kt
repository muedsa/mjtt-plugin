package com.muedsa.tvbox.mjtt.service

import com.muedsa.tvbox.api.data.DanmakuData
import com.muedsa.tvbox.api.data.DanmakuDataFlow
import com.muedsa.tvbox.api.data.MediaCard
import com.muedsa.tvbox.api.data.MediaCardRow
import com.muedsa.tvbox.api.data.MediaCardType
import com.muedsa.tvbox.api.data.MediaDetail
import com.muedsa.tvbox.api.data.MediaEpisode
import com.muedsa.tvbox.api.data.MediaHttpSource
import com.muedsa.tvbox.api.data.MediaPlaySource
import com.muedsa.tvbox.api.data.SavedMediaCard
import com.muedsa.tvbox.api.service.IMediaDetailService
import com.muedsa.tvbox.mjtt.CardColors
import com.muedsa.tvbox.mjtt.CardHeight
import com.muedsa.tvbox.mjtt.CardWidth
import com.muedsa.tvbox.mjtt.ColorCardHeight
import com.muedsa.tvbox.mjtt.ColorCardWidth
import com.muedsa.tvbox.mjtt.model.PlayUrlData
import com.muedsa.tvbox.tool.ChromeUserAgent
import com.muedsa.tvbox.tool.LenientJson
import com.muedsa.tvbox.tool.checkSuccess
import com.muedsa.tvbox.tool.feignChrome
import com.muedsa.tvbox.tool.get
import com.muedsa.tvbox.tool.parseHtml
import com.muedsa.tvbox.tool.toRequestBuild
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import okhttp3.OkHttpClient
import org.jsoup.nodes.Element
import timber.log.Timber

class MediaDetailService(
    private val mjttService: MJTTService,
    private val okHttpClient: OkHttpClient,
) : IMediaDetailService {

    override suspend fun getDetailData(mediaId: String, detailUrl: String): MediaDetail {
        val body = "${mjttService.getSiteUrl()}$detailUrl".toRequestBuild()
            .feignChrome()
            .get(okHttpClient = okHttpClient)
            .checkSuccess()
            .parseHtml()
            .body()
        val rowEl = body.selectFirst(".container:nth-child(2)")!!
            .selectFirst(".row")!!
        // 剧集信息
        val vodEl = rowEl.selectFirst(".z-pannel .z-vod")!!
        val picEl = vodEl.selectFirst(".z-list_pic")!!
        val detailPath = picEl.attr("href")
        val title = vodEl.selectFirst("h1")!!.text().trim()
        val imageUrl = picEl.absUrl("data-original")
        val ulEl = vodEl.selectFirst("ul")!!
        val liEls = ulEl.select("li")
        val descArr = mutableListOf<String>()
        var status = ""
        liEls.forEach {
            val text = it.text().trim()
            if (!text.contains("加入群组")) {
                if (text.startsWith("状态：")) {
                    status = text.removePrefix("状态：")
                }
                descArr.add(text)
            }
        }
        // 播放列表
        val vodListEls = rowEl.select(".z-pannel >.z-pannel_bd.z-vod_list")
        val rows = mutableListOf<MediaCardRow>()
        appendRows(rows = rows, rowEl = rowEl)
        return MediaDetail(
            id = detailPath,
            title = title,
            subTitle = status,
            description = descArr.joinToString("\n"),
            detailUrl = detailPath,
            backgroundImageUrl = imageUrl,
            playSourceList = vodListEls.mapIndexed { index, listEl ->
                MediaPlaySource(
                    id = "$index",
                    name = "云播${index + 1}",
                    episodeList = listEl.select("ul li").map {
                        val aEl = it.selectFirst("a")!!
                        MediaEpisode(
                            id = aEl.attr("href"),
                            name = aEl.text().trim()
                        )
                    }
                )
            },
            favoritedMediaCard = SavedMediaCard(
                id = detailPath,
                title = title,
                detailUrl = detailPath,
                coverImageUrl = imageUrl,
                cardWidth = CardWidth,
                cardHeight = CardHeight,
            ),
            rows = rows
        )
    }

    private fun appendRows(rows: MutableList<MediaCardRow>, rowEl: Element) {
        val panelEls = rowEl.select(">.z-pannel.z-pannel_bg")
        panelEls.forEach {
            if (it.selectFirst(".z-tab_wd") != null) {
                appendRelationRow(rows = rows, panelEl = it)
            } else if (it.selectFirst(">.z-pannel_bd .z-list") != null
                || it.selectFirst(">.z-pannel_bd .z-text_list") != null
            ) {
                val hdEls = it.select(">.z-pannel_hd")
                val bdEls = it.select(">.z-pannel_bd")
                if (hdEls.size == bdEls.size) {
                    hdEls.forEachIndexed { index, hdEl ->
                        MJTTService.appendRowWithUl(
                            rows = rows,
                            rowTitle = hdEl.selectFirst(".z-pannel_head .z-pannel_title")!!.text()
                                .trim(),
                            ulEl = bdEls[index].selectFirst("ul")!!
                        )
                    }
                } else {
                    Timber.w("panelEl解析失败: ${it.outerHtml()}")
                }
            }
        }
    }

    private fun appendRelationRow(rows: MutableList<MediaCardRow>, panelEl: Element) {
        val aEls = panelEl.select(".z-tab_wd a")
        rows.add(
            MediaCardRow(
                title = "相关",
                cardWidth = ColorCardWidth,
                cardHeight = ColorCardHeight,
                list = aEls.mapIndexed { index, aEl ->
                    val href = aEl.attr("href")
                    MediaCard(
                        id = href,
                        title = aEl.text().trim(),
                        detailUrl = href,
                        backgroundColor = CardColors[index % CardColors.size],
                    )
                },
                cardType = MediaCardType.NOT_IMAGE
            )
        )
    }

    override suspend fun getEpisodePlayInfo(
        playSource: MediaPlaySource,
        episode: MediaEpisode
    ): MediaHttpSource {
        val pageUrl = "${mjttService.getSiteUrl()}${episode.id}"
        val body = pageUrl.toRequestBuild()
            .feignChrome()
            .get(okHttpClient = okHttpClient)
            .checkSuccess()
            .parseHtml()
            .body()
        val scriptEl = body.selectFirst(".container .row .z-paly script:nth-child(1)")!!
        val infoJson = FF_URLS_REGEX.find(scriptEl.html())!!.groups[1]!!.value
        Timber.i("ff_urls=$infoJson")
        var infoJsonElement = LenientJson.parseToJsonElement(infoJson)
        val infoDataJsonElement = infoJsonElement.jsonObject["Data"]
        check(infoDataJsonElement != null && infoDataJsonElement !is JsonNull) { "解析播放源失败" }
        var source: PlayUrlData? = null
        var data: List<String>? = null
        if (infoDataJsonElement is JsonArray) {
            val infoDataList =
                LenientJson.decodeFromJsonElement<List<PlayUrlData>>(infoDataJsonElement)
            for (s in infoDataList) {
                data = s.playUrls.find { urlData -> urlData[2] == episode.id }
                if (data != null) {
                    source = s
                    break
                }
            }
        } else if (infoDataJsonElement is JsonObject) {
            val infoDataMap =
                LenientJson.decodeFromJsonElement<Map<String, PlayUrlData>>(infoDataJsonElement)
            for ((_, v) in infoDataMap) {
                data = v.playUrls.find { urlData -> urlData[2] == episode.id }
                if (data != null) {
                    source = v
                    break
                }
            }
        }
        val playUrl: String = data?.get(1) ?: ""
        checkNotNull(source) { "播放源未找到" }
        check(playUrl.isNotBlank()) { "播放源未找到" }
        if (playUrl.endsWith(".m3u8", true)) {
            return MediaHttpSource(
                url = playUrl,
                httpHeaders = mapOf(
                    "User-Agent" to ChromeUserAgent,
                    "Referer" to "${mjttService.getSiteUrl()}/"
                )
            )
        } else if (source.playName.startsWith("huobo")) {
            return getHuoboMediaHttpSource(playUrl)
        } else {
            throw RuntimeException("不支持的播放源")
        }
    }

    private fun getHuoboMediaHttpSource(key: String): MediaHttpSource {
        val body = "https://php.playerla.com/mjplay/?id=$key".toRequestBuild()
            .feignChrome()
            .get(okHttpClient = okHttpClient)
            .checkSuccess()
            .parseHtml()
            .body()
        return MediaHttpSource(
            url = CURRENT_URL_REGEX.find(body.html())!!.groups[1]!!.value,
            httpHeaders = mapOf(
                "User-Agent" to ChromeUserAgent,
                "Referer" to "https://php.playerla.com/"
            )
        )
    }

    override suspend fun getEpisodeDanmakuDataList(episode: MediaEpisode): List<DanmakuData> =
        emptyList()

    override suspend fun getEpisodeDanmakuDataFlow(episode: MediaEpisode): DanmakuDataFlow? = null

    companion object {
        val FF_URLS_REGEX = "var ff_urls='(\\{.*?\\})';".toRegex()
        val CURRENT_URL_REGEX = "var currentUrl = '(.*?)';".toRegex()
    }
}