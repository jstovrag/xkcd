package com.xk.cd.data.di.api

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.xk.cd.data.base.config.NetworkConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * Implementation of [ApiFactory] providing instances using Retrofit2
 */
class RetrofitApiFactory @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val networkConfig: NetworkConfig
) :
    ApiFactory {
    override fun <T> buildApi(type: Class<T>?): T {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(networkConfig.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(type)
    }

    override fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(networkConfig.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

}
