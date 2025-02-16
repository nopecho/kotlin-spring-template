package com.nopecho.support.core

import com.nopecho.support.core.fixture.ConcurrentUtils
import com.nopecho.support.core.fixture.randomSleep
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicLong

class ConcurrentUtilsTest {

    @Test
    fun `동시 실행 테스트 - 코루틴`() {
        val callCount = 1000
        val counter = AtomicLong()

        ConcurrentUtils.runWithCoroutine(concurrentCount = callCount) {
            randomSleep(0, 30)
            counter.incrementAndGet()
        }

        counter.get() shouldBe callCount
    }

    @Test
    fun `동시 실행 테스트 - 가상 스레드`() {
        val callCount = 1000
        val counter = AtomicLong()

        ConcurrentUtils.runWithThread(concurrentCount = callCount) {
            randomSleep(0, 30)
            counter.incrementAndGet()
        }

        counter.get() shouldBe callCount
    }
}