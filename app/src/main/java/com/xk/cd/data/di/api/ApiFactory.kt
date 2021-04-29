package com.xk.cd.data.di.api

import retrofit2.Retrofit

/**
 * This represents something that can produce implementation of API
 */
interface ApiFactory {
    /**
     * Should produce instance of T
     */
    fun <T> buildApi(type: Class<T>?): T
    fun buildRetrofit(): Retrofit?
}
