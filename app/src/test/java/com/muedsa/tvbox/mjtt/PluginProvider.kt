package com.muedsa.tvbox.mjtt

import com.muedsa.tvbox.api.plugin.TvBoxContext

val TestPlugin by lazy {
    MJTTPlugin(
        tvBoxContext = TvBoxContext(
            screenWidth = 1920,
            screenHeight = 1080,
            debug = true,
            store = FakePluginPrefStore()
        )
    )
}