package com.xk.cd.data.repositories.comic

import com.xk.cd.common.extensions.asBody
import com.xk.cd.data.base.error.ErrorUtils
import com.xk.cd.data.models.Comic
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val comicApi: ComicApi,
    private val errorMapper: ErrorUtils
) : ComicRepository {

    override suspend fun getCurrentComic(): Comic {
        return comicApi.getCurrentComicAsync().await().asBody(errorMapper)
    }

    override suspend fun getComic(comicNumber: Int): Comic {
        return comicApi.getComicAsync(comicNumber).await().asBody(errorMapper)
    }
}

interface ComicRepository {
    suspend fun getCurrentComic(): Comic
    suspend fun getComic(comicNumber: Int): Comic
}

interface ComicApi {
    @GET("/info.0.json")
    fun getCurrentComicAsync(): Deferred<Response<Comic>>

    @GET("/{comicNumber}/info.0.json")
    fun getComicAsync(@Path("comicNumber") comicNumber: Int): Deferred<Response<Comic>>
}
