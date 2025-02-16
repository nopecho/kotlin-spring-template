package com.nopecho.support.core.fixture

import org.springframework.util.StopWatch
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit
import kotlin.random.Random

fun stopWatch(name: String = "", timeUnit: TimeUnit = TimeUnit.MILLISECONDS, block: () -> Unit) {
    StopWatch(name).run {
        start(name)
        block()
        stop()
        println(prettyPrint(timeUnit))
    }
}

fun randomInt(min: Int = 1, max: Int = Int.MAX_VALUE): Int {
    return (min..max).random()
}

fun randomDouble(min: Double = -1.0, max: Double = 2.0): Double {
    return Random.nextDouble(min, max)
}

fun randomSleep() {
    val random = ThreadLocalRandom.current().nextInt(300, 500)
    Thread.sleep(random.toLong())
}