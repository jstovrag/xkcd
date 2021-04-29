package com.xk.cd.data.store.httpcache

import android.content.Context
import okhttp3.Cache
import okhttp3.internal.io.FileSystem
import java.io.File
import javax.inject.Inject

class HttpCache @Inject constructor(private val context: Context) {

    fun getCache(): Cache {
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        val cacheSize = 10 * 1024 * 1024 // 10 MiB

        return returnCache(httpCacheDirectory, cacheSize.toLong())
    }

    fun clearAllHttpCache() {
        File(context.cacheDir, "http-cache").deleteRecursively()
    }

    private fun returnCache(directory: File, maxSize: Long): Cache {
        val constructor =
            Cache::class.java.getDeclaredConstructor(File::class.java, Long::class.java, FileSystem::class.java)
        constructor.isAccessible = true
        return constructor.newInstance(directory, maxSize)
    }
}
