package com.muedsa.tvbox.mjtt.service

import com.muedsa.tvbox.api.data.MediaCardRow
import com.muedsa.tvbox.api.service.IMainScreenService
import com.muedsa.tvbox.tool.ChromeUserAgent
import org.jsoup.Jsoup
import timber.log.Timber

class MainScreenService : IMainScreenService {

    override suspend fun getRowsData(): List<MediaCardRow> {
        val url = "${MJTTService.getSiteUrl()}/"
        val doc = Jsoup.connect(url)
            .userAgent(ChromeUserAgent)
            .get()
        val panelEls = doc.body().select(".container .row .z-pannel")
        val rows = mutableListOf<MediaCardRow>()
        panelEls.forEachIndexed { panelIndex, panelEl ->
            if (panelIndex == 0) {
                // 热门
                MJTTService.appendRowWithUl(
                    rows = rows,
                    rowTitle = "热门推荐",
                    ulEl = panelEl.selectFirst(".z-pannel_bd ul")!!
                )
            } else if (panelIndex == 1) {
                // 周更榜
                MJTTService.appendWeekRows(
                    rows = rows,
                    weekUlEls = panelEl.select(".tab-content .tab-pane ul")
                )
            } else {
                val hdEls = panelEl.select(">.z-pannel_hd")
                val bdEls = panelEl.select(">.z-pannel_bd")
                if (hdEls.size == bdEls.size) {
                    hdEls.forEachIndexed { index, hdEl ->
                        val bdEl = bdEls[index].selectFirst("ul")
                        if (bdEl != null) {
                            MJTTService.appendRowWithUl(
                                rows = rows,
                                rowTitle = hdEl.select(".z-pannel_head .z-pannel_title").text().trim(),
                                ulEl = bdEls[index].selectFirst("ul")!!
                            )
                        }
                    }
                } else {
                    Timber.w("panelEl解析失败: ${panelEl.outerHtml()}")
                }
            }
        }
        return rows
    }
}