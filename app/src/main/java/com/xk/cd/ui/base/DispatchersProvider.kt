package com.xk.cd.ui.base

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {
    /**
     * I/O dispatcher - Optimized for disk and network IO off the main thread
     */
    fun io(): CoroutineDispatcher

    /**
     * Main dispatcher - interacts with UI
     */
    fun main(): CoroutineDispatcher

    /**
     * Default dispatcher - Optimized for CPU intensive work off the main thread
     */
    fun computation(): CoroutineDispatcher

    /**
     * The Dispatchers.Unconfined coroutine dispatcher starts coroutine in the caller thread,
     * but only until the first suspension point.
     * After suspension it resumes in the thread that is fully determined by
     * the suspending function that was invoked.
     */
    fun unconfined(): CoroutineDispatcher
}
