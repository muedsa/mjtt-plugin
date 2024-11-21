package com.muedsa.tvbox.mjtt.service

import com.muedsa.tvbox.api.data.MediaCard
import com.muedsa.tvbox.api.data.MediaCardRow
import com.muedsa.tvbox.api.data.MediaCardType
import com.muedsa.tvbox.mjtt.CardColors
import com.muedsa.tvbox.mjtt.CardHeight
import com.muedsa.tvbox.mjtt.CardWidth
import com.muedsa.tvbox.mjtt.ColorCardHeight
import com.muedsa.tvbox.mjtt.ColorCardWidth
import com.muedsa.tvbox.mjtt.JumpUrlRegex
import com.muedsa.tvbox.tool.checkSuccess
import com.muedsa.tvbox.tool.feignChrome
import com.muedsa.tvbox.tool.get
import com.muedsa.tvbox.tool.parseHtml
import com.muedsa.tvbox.tool.toRequestBuild
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import timber.log.Timber

class MJTTService(
    private val okHttpClient: OkHttpClient
) {
    private var siteUrl: String? = null
    private var siteScheme: String? = null

    @Synchronized
    fun getSiteUrl(): String {
        try {
            if (siteUrl == null) {
                val doc = "http://mjtt.io".toRequestBuild()
                    .feignChrome()
                    .get(okHttpClient = okHttpClient)
                    .checkSuccess()
                    .parseHtml()
                siteUrl = JumpUrlRegex.find(doc.html())!!.groups[1]!!.value.removeSuffix("/")
                siteScheme = siteUrl!!.toHttpUrl().scheme
            }
        } catch (throwable: Throwable) {
            throw RuntimeException("获取站点URL失败", throwable)
        }
        return siteUrl!!
    }

    companion object {
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
                Timber.w("未知的ul: ${ulEl.outerHtml()}")
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
                coverImageUrl = picEl.absUrl("data-original"),
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
                backgroundColor = CardColors[(index + rowIndex) % CardColors.size],
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
                backgroundColor = CardColors[(index + rowIndex) % CardColors.size]
            )
        }
    }
}