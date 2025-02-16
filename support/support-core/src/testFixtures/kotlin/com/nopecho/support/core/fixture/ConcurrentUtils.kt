package com.nopecho.support.core.fixture

import com.nopecho.support.core.VIRTUAL
import kotlinx.coroutines.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object ConcurrentUtils {

    fun runWithCoroutine(
        concurrentCount: Int = 200,
        dispatcher: CoroutineDispatcher = Dispatchers.VIRTUAL,
        task: () -> Unit
    ) {
        val startSignal = CompletableDeferred<Unit>()
        runBlocking(dispatcher) {
            val jobs = (1..concurrentCount).map {
                async {
                    runCatching {
                        startSignal.await()
                        task()
                    }.getOrElse {
                        it.printStackTrace()
                    }
                }
            }
            startSignal.complete(Unit)
            jobs.awaitAll()
        }
    }

    fun runWithThread(
        concurrentCount: Int = 200,
        task: () -> Unit
    ) {
        val (executor, startLatch, doneLatch) = setupThread(concurrentCount)
        repeat(concurrentCount) {
            executor.submit {
                try {
                    startLatch.await()
                    task()
                } catch (e: Throwable) {
                    e.printStackTrace()
                } finally {
                    doneLatch.countDown()
                }
            }
        }
        releaseThread(executor, startLatch, doneLatch)
    }

    private fun setupThread(threadCount: Int): Triple<ExecutorService, CountDownLatch, CountDownLatch> {
        // 가상 스레드 풀
        val executor = Executors.newVirtualThreadPerTaskExecutor()
        val startLatch = CountDownLatch(1)
        val doneLatch = CountDownLatch(threadCount)
        return Triple(executor, startLatch, doneLatch)
    }

    private fun releaseThread(
        executor: ExecutorService,
        startLatch: CountDownLatch,
        doneLatch: CountDownLatch,
    ) {
        startLatch.countDown()
        doneLatch.await()
        executor.shutdown()
    }
}