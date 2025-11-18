package com.muedsa.tvbox.mjtt

import com.muedsa.tvbox.api.plugin.TvBoxContext
import com.muedsa.tvbox.mjtt.service.MJTTService
import com.muedsa.tvbox.tool.IPv6Checker
import com.muedsa.tvbox.tool.PluginCookieJar
import com.muedsa.tvbox.tool.SharedCookieSaver
import com.muedsa.tvbox.tool.createOkHttpClient
import java.util.concurrent.TimeUnit

val TestPluginPrefStore = FakePluginPrefStore()

val TestOkHttpClient by lazy {
    createOkHttpClient(
        debug = true,
        cookieJar = PluginCookieJar(saver = SharedCookieSaver(store = TestPluginPrefStore)),
        onlyIpv4 = false,
    ) {
//        proxy(
//            Proxy(
//                Proxy.Type.SOCKS,
//                InetSocketAddress("localhost", 23333)
//            )
//        )
        callTimeout(40, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
    }
}

val TestMJTTService by lazy { MJTTService(okHttpClient = TestOkHttpClient) }

val TestPlugin by lazy {
    MJTTPlugin(
        tvBoxContext = TvBoxContext(
            screenWidth = 1920,
            screenHeight = 1080,
            debug = true,
            store = TestPluginPrefStore,
            iPv6Status = IPv6Checker.checkIPv6Support(),
        )
    )
}