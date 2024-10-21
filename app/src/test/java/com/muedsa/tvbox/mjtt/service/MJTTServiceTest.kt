package com.muedsa.tvbox.mjtt.service

import org.junit.Test

class MJTTServiceTest {

    @Test
    fun getSiteUrlTest() {
        val siteUrl = MJTTService.getSiteUrl()
        check(siteUrl.isNotBlank())
        println(siteUrl)
    }

}