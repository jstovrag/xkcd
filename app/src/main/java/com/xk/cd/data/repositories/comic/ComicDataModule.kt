package com.xk.cd.data.repositories.comic

import com.xk.cd.data.di.api.ApiFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ComicDataModule {

    @Provides
    @Singleton
    fun provideComicRepository(comicRepositoryImpl: ComicRepositoryImpl): ComicRepository {
        return comicRepositoryImpl
    }

    @Provides
    @Singleton
    fun providesComicApi(apiFactory: ApiFactory): ComicApi =
        apiFactory.buildApi(ComicApi::class.java)
}
