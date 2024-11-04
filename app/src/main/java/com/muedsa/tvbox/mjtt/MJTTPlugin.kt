package com.muedsa.tvbox.mjtt

import com.muedsa.tvbox.api.plugin.IPlugin
import com.muedsa.tvbox.api.plugin.PluginOptions
import com.muedsa.tvbox.api.plugin.TvBoxContext
import com.muedsa.tvbox.api.service.IMainScreenService
import com.muedsa.tvbox.api.service.IMediaDetailService
import com.muedsa.tvbox.api.service.IMediaSearchService
import com.muedsa.tvbox.mjtt.service.MainScreenService
import com.muedsa.tvbox.mjtt.service.MediaDetailService
import com.muedsa.tvbox.mjtt.service.MediaSearchService
import com.muedsa.tvbox.tool.PluginCookieStore
import com.muedsa.tvbox.tool.SharedCookieSaver

class MJTTPlugin(tvBoxContext: TvBoxContext) : IPlugin(tvBoxContext = tvBoxContext) {

    override var options: PluginOptions = PluginOptions(enableDanDanPlaySearch = false)

    override suspend fun onInit() {}
    override suspend fun onLaunched() {}

    private val cookieStore by lazy { PluginCookieStore(saver = SharedCookieSaver(store = tvBoxContext.store)) }
    private val mainScreenService by lazy { MainScreenService(cookieStore) }
    private val mediaDetailService by lazy { MediaDetailService(cookieStore) }
    private val mediaSearchService by lazy { MediaSearchService(cookieStore) }

    override fun provideMainScreenService(): IMainScreenService = mainScreenService

    override fun provideMediaDetailService(): IMediaDetailService = mediaDetailService

    override fun provideMediaSearchService(): IMediaSearchService = mediaSearchService
}