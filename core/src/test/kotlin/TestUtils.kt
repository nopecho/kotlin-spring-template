import java.util.concurrent.CountDownLatch

fun concurrencyCall(requestSize: Int = 100, task: () -> Unit): List<Throwable> {
    val throwable = mutableListOf<Throwable>()
    val startLatch = CountDownLatch(1)
    val doneLatch = CountDownLatch(requestSize)

    repeat(requestSize) {
        Thread {
            try {
                startLatch.await()
                task()
            } catch (e: Throwable) {
                throwable.add(e)
            } finally {
                doneLatch.countDown()
            }
        }.start()
    }

    Thread.sleep(100)
    startLatch.countDown()
    doneLatch.await()
    return throwable
}