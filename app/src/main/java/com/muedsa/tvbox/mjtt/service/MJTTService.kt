package com.muedsa.tvbox.mjtt.service

import com.muedsa.tvbox.api.data.MediaCard
import com.muedsa.tvbox.api.data.MediaCardRow
import com.muedsa.tvbox.api.data.MediaCardType
import com.muedsa.tvbox.mjtt.CardHeight
import com.muedsa.tvbox.mjtt.CardWidth
import com.muedsa.tvbox.mjtt.ColorCardHeight
import com.muedsa.tvbox.mjtt.ColorCardWidth
import com.muedsa.tvbox.mjtt.JumpUrlRegex
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import timber.log.Timber

object MJTTService {
    val CARD_COLORS = listOf(
        0XFF_15_5A_32,
        0XFF_09_53_45,
        0XFF_15_43_61,
        0XFF_42_49_49,
        0XFF_78_42_13
    )

    private var siteUrl: String? = null
    private var siteScheme: String? = null

    @Synchronized
    fun getSiteUrl(): String {
        try {
            if (siteUrl == null) {
                val doc = Jsoup.connect("http://mjtt.io")
                    .get()
                siteUrl = JumpUrlRegex.find(doc.html())!!.groups[1]!!.value.removeSuffix("/")
                siteScheme = siteUrl!!.toHttpUrl().scheme
            }
        } catch (throwable: Throwable) {
            throw RuntimeException("获取站点URL失败", throwable)
        }
        return siteUrl!!
    }

    fun resolveUrl(url: String): String {
        return if (url.isBlank() || url.startsWith("http://") || url.startsWith("https://")) {
            url
        } else if (url.startsWith("//:")) {
            "$siteScheme$url"
        } else if (url.startsWith("/")){
            "$siteUrl$url"
        } else {
            "$siteUrl/$url"
        }
    }

    fun appendRowWithUl(rows: MutableList<MediaCardRow>, rowTitle: String, ulEl: Element) {
        if (ulEl.hasClass("z-list")) {
            rows.add(MediaCardRow(
                title = rowTitle,
                cardWidth = CardWidth,
                cardHeight = CardHeight,
                list = ulEl.select("li").map { convertImageLiToImageCard(it) }
            ))
        } else if (ulEl.hasClass("z-text_list")) {
            val rowIndex = rows.count { it.cardType == MediaCardType.NOT_IMAGE }
            rows.add(
                MediaCardRow(
                    title = rowTitle,
                    cardWidth = ColorCardWidth,
                    cardHeight = ColorCardHeight,
                    list = ulEl.select("li").mapIndexed { index, liEl ->
                        convertTextLiToImageCard(
                            rowIndex = rowIndex,
                            index = index,
                            liEl = liEl,
                        )
                    },
                    cardType = MediaCardType.NOT_IMAGE
                )
            )
        } else {
            Timber.w("未知的ul:" + ulEl.outerHtml())
        }
    }

    private fun convertImageLiToImageCard(liEl: Element): MediaCard {
        val picEl = liEl.selectFirst(".z-list_pic")!!
        val title = liEl.selectFirst(".z-list_title")!!.text().trim()
        val status = liEl.selectFirst(".z-status")?.text()?.trim() ?: ""
        val type = liEl.selectFirst(".z-type")?.text()?.trim() ?: ""
        return MediaCard(
            id = picEl.attr("href"),
            title = title,
            detailUrl = picEl.attr("href"),
            coverImageUrl = resolveUrl(picEl.attr("data-original")),
            subTitle = "$status $type"
        )
    }

    private fun convertTextLiToImageCard(rowIndex: Int, index: Int, liEl: Element ): MediaCard {
        val aEl = liEl.selectFirst("a")!!
        val spanEl = liEl.selectFirst("span")!!
        return MediaCard(
            id = aEl.attr("href"),
            title = aEl.text().trim(),
            detailUrl = aEl.attr("href"),
            backgroundColor = CARD_COLORS[(index + rowIndex) % CARD_COLORS.size],
            subTitle = spanEl.text().trim()
        )
    }

    private val WEEK_NAMES = listOf("周一", "周二", "周三", "周四", "周五", "周六", "周日")

    private fun getWeekName(index: Int) =
        if (index < WEEK_NAMES.size) WEEK_NAMES[index] else "榜${index + 1}"


    fun appendWeekRows(rows: MutableList<MediaCardRow>, weekUlEls: Elements) {
        weekUlEls.forEachIndexed { ulIndex, ulEl ->
            val rowIndex = rows.count { c -> c.cardType == MediaCardType.NOT_IMAGE }
            rows.add(MediaCardRow(
                title = getWeekName(ulIndex),
                cardWidth = ColorCardWidth,
                cardHeight = ColorCardHeight,
                list = ulEl.select("li")
                    .mapIndexed { index, liEl ->
                        convertWeekLiToMediaCard(
                            rowIndex = rowIndex,
                            index = index,
                            liEl = liEl
                        )
                    },
                cardType = MediaCardType.NOT_IMAGE
            ))
        }
    }

    private fun convertWeekLiToMediaCard(rowIndex: Int, index: Int, liEl: Element): MediaCard {
        val aEl = liEl.selectFirst(".z-list_title a")!!
        return MediaCard(
            id = aEl.attr("href"),
            title = aEl.attr("title"),
            detailUrl = aEl.attr("href"),
            backgroundColor = CARD_COLORS[(index + rowIndex) % CARD_COLORS.size]
        )
    }
}