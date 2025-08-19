package com.muedsa.tvbox.mjtt.service

import com.muedsa.tvbox.tool.IPv6Checker
import com.muedsa.tvbox.tool.createOkHttpClient
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class MJTTServiceTest {

    private val okHttpClient = createOkHttpClient(
        debug = true,
        onlyIpv4 = IPv6Checker.checkIPv6Support() != IPv6Checker.IPv6Status.SUPPORTED
    )

    @Test
    fun getSiteUrlTest() {
        val siteUrl = MJTTService(
            okHttpClient = okHttpClient
        ).getSiteUrl()
        check(siteUrl.isNotBlank())
        println(siteUrl)
    }

}