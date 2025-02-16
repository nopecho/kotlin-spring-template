package com.nopecho.support.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

/**
 * A virtual thread per task executor.
 * @return a new [CoroutineDispatcher] that uses a virtual thread per task executor.
 */
val Dispatchers.VIRTUAL: CoroutineDispatcher
    get() = Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()