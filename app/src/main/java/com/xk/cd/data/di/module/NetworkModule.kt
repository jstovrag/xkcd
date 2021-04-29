package com.xk.cd.data.di.module

import com.google.gson.Gson
import com.xk.cd.data.base.config.DefaultNetworkConfig
import com.xk.cd.data.base.config.NetworkConfig
import com.xk.cd.data.di.api.ApiFactory
import com.xk.cd.data.store.httpcache.HttpCache
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.*
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Module which should provide core networking dependencies:
 * * Http client (OkHttp)
 * * ApiFactory (Retrofit)
 * * Core interceptors (Http logging interceptor and authentication can be created if needed)
 *
 *
 * Additional interceptors may be provided from other modules, but they must be at [Singleton] scope
 * They also need to be provided [IntoSet] since many interceptors may be used
 * Order of adding interceptors is not enforced!
 */
@Module
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun provideDefaultRetrofitApiFactory(apiFactory: ApiFactory): ApiFactory

    @Binds
    @Singleton
    abstract fun provideDefaultOkHttpClient(okHttpClient: OkHttpClient): OkHttpClient

    @Binds
    @Singleton
    abstract fun provideDefaultNetworkConfig(defaultNetworkConfig: DefaultNetworkConfig): NetworkConfig?

    companion object {

//        @Provides
//        @Singleton
//        fun provideUnAuthenticatedOkHttpClient(
//            networkConfig: NetworkConfig,
//            authenticator: Authenticator,
//            dispatcher: Dispatcher,
//            unauthenticatedInterceptors: Set<Interceptor?>?,
//            interceptors: Set<Interceptor?>?,
//            httpCache: HttpCache
//        ): OkHttpClient {
//            val allInterceptors: MutableSet<Interceptor> = HashSet()
//            allInterceptors.addAll(interceptors)
//            allInterceptors.addAll(unauthenticatedInterceptors)
//            return createOkHttpClient(
//                networkConfig,
//                authenticator,
//                dispatcher,
//                allInterceptors,
//                httpCache
//            )
//        }
//
//        @Provides
//        @Singleton
//        fun provideAuthenticatedOkHttpClient(
//            networkConfig: NetworkConfig,
//            authenticator: Authenticator,
//            dispatcher: Dispatcher,
//            authenticatedInterceptors: Set<Interceptor?>?,
//            interceptors: Set<Interceptor?>?,
//            httpCache: HttpCache
//        ): OkHttpClient {
//            val allInterceptors: MutableSet<Interceptor> = HashSet()
//            allInterceptors.addAll(interceptors)
//            allInterceptors.addAll(authenticatedInterceptors)
//            return createOkHttpClient(
//                networkConfig,
//                authenticator,
//                dispatcher,
//                allInterceptors,
//                httpCache
//            )
//        }

        private fun createOkHttpClient(
            networkConfig: NetworkConfig,
            authenticator: Authenticator,
            dispatcher: Dispatcher,
            interceptors: Set<Interceptor>,
            httpCache: HttpCache
        ): OkHttpClient {
            val builder = OkHttpClient.Builder()

            builder.authenticator(authenticator)
                .dispatcher(dispatcher)
            for (interceptor in interceptors) {
                builder.addInterceptor(interceptor)
            }
            return builder
                .connectTimeout(networkConfig.connectTimeoutInMs, TimeUnit.MILLISECONDS)
                .readTimeout(networkConfig.readTimeoutInMs, TimeUnit.MILLISECONDS)
                .writeTimeout(networkConfig.writeTimeoutInMs, TimeUnit.MILLISECONDS)
                .cache(httpCache.getCache())
                .build()
        }

//        @Provides
//        @Singleton
//        fun provideAuthenticatedRetrofitApiFactory(
//            okHttpClient: OkHttpClient?,
//            networkConfig: NetworkConfig?
//        ): ApiFactory {
//            return RetrofitApiFactory(okHttpClient, networkConfig)
//        }

//        @Provides
//        @Singleton
//        fun provideUnauthenticatedRetrofitApiFactory(
//            okHttpClient: OkHttpClient?,
//            networkConfig: NetworkConfig?
//        ): ApiFactory {
//            return RetrofitApiFactory(okHttpClient, networkConfig)
//        }
    }
}
