package com.muedsa.tvbox.mjtt.service

import com.muedsa.tvbox.mjtt.TestPlugin
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class MainScreenServiceTest {

    private val service = TestPlugin.provideMainScreenService()

    @Test
    fun getRowsDataTest() = runTest {
        val rows = service.getRowsData()
        check(rows.isNotEmpty())
        rows.forEach { row ->
            check(row.title.isNotEmpty())
            check(row.list.isNotEmpty())
            check(row.cardWidth > 0)
            check(row.cardHeight > 0)
            println("${row.title} ${row.cardWidth}X${row.cardHeight} ${row.cardType}")
            row.list.forEach {
                check(it.id.isNotEmpty())
                check(it.title.isNotEmpty())
                check(it.detailUrl.isNotEmpty())
                println("${it.title} ${it.id} ${it.detailUrl} ${it.coverImageUrl}")
            }
        }
    }

}