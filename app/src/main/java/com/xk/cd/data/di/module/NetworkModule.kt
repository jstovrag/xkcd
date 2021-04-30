package com.xk.cd.data.di.module

import com.xk.cd.data.base.config.DefaultNetworkConfig
import com.xk.cd.data.base.config.NetworkConfig
import com.xk.cd.data.di.api.ApiFactory
import com.xk.cd.data.di.api.RetrofitApiFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Authenticator
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
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
    @Module
    companion object {
        @Provides
        @Singleton
        @Authenticated(false)
        @JvmStatic
        fun provideAuthenticator(): Authenticator {
            return Authenticator.NONE
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideDispatcher(): Dispatcher {
            return Dispatcher()
        }

        @Provides
        @Singleton
        @IntoSet
        @JvmStatic
        fun provideLoggingInterceptor(): Interceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }

        @Provides
        @Singleton
        @JvmStatic
        @Authenticated(false)
        fun provideUnAuthenticatedOkHttpClient(
            networkConfig: NetworkConfig,
            @Authenticated(false)
            dispatcher: Dispatcher
        ): OkHttpClient = createOkHttpClient(
            networkConfig,
            dispatcher
        )

        @Provides
        @Singleton
        @JvmStatic
        @Authenticated(true)
        fun provideAuthenticatedOkHttpClient(
            networkConfig: NetworkConfig,
            dispatcher: Dispatcher
        ): OkHttpClient = createOkHttpClient(
            networkConfig,
            dispatcher
        )

        private fun createOkHttpClient(
            networkConfig: NetworkConfig,
            dispatcher: Dispatcher
        ): OkHttpClient {
            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
                .dispatcher(dispatcher)

            builder.addInterceptor(provideLoggingInterceptor())

            return builder
                .connectTimeout(networkConfig.connectTimeoutInMs, TimeUnit.MILLISECONDS)
                .readTimeout(networkConfig.readTimeoutInMs, TimeUnit.MILLISECONDS)
                .writeTimeout(networkConfig.writeTimeoutInMs, TimeUnit.MILLISECONDS)
                .build()
        }

        @Provides
        @Singleton
        @JvmStatic
        @Authenticated(true)
        fun provideAuthenticatedRetrofitApiFactory(
            @Authenticated(true) okHttpClient: OkHttpClient,
            networkConfig: NetworkConfig
        ): ApiFactory = RetrofitApiFactory(okHttpClient, networkConfig)

        @Provides
        @Singleton
        @JvmStatic
        @Authenticated(false)
        fun provideUnauthenticatedRetrofitApiFactory(
            @Authenticated(false) okHttpClient: OkHttpClient,
            networkConfig: NetworkConfig
        ): ApiFactory = RetrofitApiFactory(okHttpClient, networkConfig)
    }

//    @Multibinds
//    @Singleton
//    @Authenticated(false)
//    abstract fun bindEmptyUnauthenticatedInterceptors(): Set<@JvmSuppressWildcards Interceptor>

    @Binds
    @Singleton
    abstract fun provideDefaultRetrofitApiFactory(@Authenticated(true) apiFactory: ApiFactory): ApiFactory

    @Binds
    @Singleton
    abstract fun provideDefaultOkHttpClient(@Authenticated(true) okHttpClient: OkHttpClient): OkHttpClient

    @Binds
    @Singleton
    abstract fun provideDefaultNetworkConfig(defaultNetworkConfig: DefaultNetworkConfig): NetworkConfig
}

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class Authenticated(val isAuthenticated: Boolean)
