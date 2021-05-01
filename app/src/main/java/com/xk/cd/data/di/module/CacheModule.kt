package com.xk.cd.data.di.module

import android.content.Context
import com.xk.cd.data.models.comic.Comic
import com.xk.cd.data.store.RealmCache
import com.xk.cd.data.store.httpcache.HttpCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module which should provide core caching/database dependencies:
 * * Realm module
 *
 */
@Module
abstract class CacheModule {

    companion object {
        @Singleton
        @Provides
        fun provideRealmCache(): RealmCache<Comic> {
            return RealmCache()
        }

        @Singleton
        @Provides
        fun provideHttpCache(context: Context): HttpCache {
            return HttpCache(context)
        }
    }
}

