package com.muedsa.tvbox.mjtt.service

import com.muedsa.tvbox.mjtt.TestOkHttpClient
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class MJTTServiceTest {

    @Test
    fun getSiteUrlTest() {
        val siteUrl = MJTTService(
            okHttpClient = TestOkHttpClient
        ).getSiteUrl()
        check(siteUrl.isNotBlank())
        println(siteUrl)
    }

}