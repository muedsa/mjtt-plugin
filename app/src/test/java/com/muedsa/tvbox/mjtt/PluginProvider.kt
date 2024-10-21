package com.muedsa.tvbox.mjtt

import com.muedsa.tvbox.api.plugin.TvBoxContext

val TestPlugin by lazy { MJTTPlugin(TvBoxContext(1080, 1920, true)) }