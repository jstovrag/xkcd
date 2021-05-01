package com.xk.cd.data.comic

import com.xk.cd.common.extensions.asBody
import com.xk.cd.data.base.error.ErrorUtils
import com.xk.cd.data.models.comic.Comic
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicRepositoryImpl @Inject constructor(
    private val comicApi: ComicApi,
    private val errorMapper: ErrorUtils
) : ComicRepository {

    override suspend fun getLastComic(): Comic {
        return comicApi.getLastComicAsync().await().asBody(errorMapper)
    }

    override suspend fun getComic(num: Int): Comic {
        return comicApi.getComicAsync(num).await().asBody(errorMapper)
    }
}

interface ComicRepository {
    suspend fun getLastComic(): Comic
    suspend fun getComic(num: Int): Comic
}

interface ComicApi {
    @GET("/info.0.json")
    fun getLastComicAsync(): Deferred<Response<Comic>>

    @GET("/{num}/info.0.json")
    fun getComicAsync(@Path("num") num: Int): Deferred<Response<Comic>>
}

