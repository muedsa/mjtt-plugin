package com.muedsa.tvbox.mjtt

import com.muedsa.tvbox.api.plugin.IPlugin
import com.muedsa.tvbox.api.plugin.PluginOptions
import com.muedsa.tvbox.api.plugin.TvBoxContext
import com.muedsa.tvbox.api.service.IMainScreenService
import com.muedsa.tvbox.api.service.IMediaDetailService
import com.muedsa.tvbox.api.service.IMediaSearchService
import com.muedsa.tvbox.mjtt.service.MJTTService
import com.muedsa.tvbox.mjtt.service.MainScreenService
import com.muedsa.tvbox.mjtt.service.MediaDetailService
import com.muedsa.tvbox.mjtt.service.MediaSearchService
import com.muedsa.tvbox.tool.IPv6Checker
import com.muedsa.tvbox.tool.PluginCookieJar
import com.muedsa.tvbox.tool.SharedCookieSaver
import com.muedsa.tvbox.tool.createOkHttpClient
import java.util.concurrent.TimeUnit

class MJTTPlugin(tvBoxContext: TvBoxContext) : IPlugin(tvBoxContext = tvBoxContext) {

    override var options: PluginOptions = PluginOptions(enableDanDanPlaySearch = false)

    override suspend fun onInit() {}
    override suspend fun onLaunched() {}

    private val okHttpClient by lazy {
        createOkHttpClient(
            debug = tvBoxContext.debug,
            cookieJar = PluginCookieJar(saver = SharedCookieSaver(store = tvBoxContext.store)),
            onlyIpv4 = tvBoxContext.iPv6Status != IPv6Checker.IPv6Status.SUPPORTED
        ) {
            callTimeout(40, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
        }
    }
    private val mtjjService by lazy { MJTTService(okHttpClient = okHttpClient) }
    private val mainScreenService by lazy {
        MainScreenService(
            mjttService = mtjjService,
            okHttpClient = okHttpClient,
        )
    }
    private val mediaDetailService by lazy {
        MediaDetailService(
            mjttService = mtjjService,
            okHttpClient = okHttpClient,
        )
    }
    private val mediaSearchService by lazy {
        MediaSearchService(
            mjttService = mtjjService,
            okHttpClient = okHttpClient,
        )
    }

    override fun provideMainScreenService(): IMainScreenService = mainScreenService

    override fun provideMediaDetailService(): IMediaDetailService = mediaDetailService

    override fun provideMediaSearchService(): IMediaSearchService = mediaSearchService
}