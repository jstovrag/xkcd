package com.xk.cd.data.comic

import com.xk.cd.data.di.api.ApiFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class ComicDataModule {

    @Binds
    @Singleton
    abstract fun provideComicRepository(comicRepositoryImpl: ComicRepositoryImpl): ComicRepository

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun providesComicApi(apiFactory: ApiFactory): ComicApi =
            apiFactory.buildApi(ComicApi::class.java)
    }
}
