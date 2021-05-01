package com.xk.cd.data.base.error

import com.xk.cd.data.di.api.ApiFactory
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class ErrorUtils @Inject constructor(
    private val apiFactory: ApiFactory
) {
    fun parseError(response: Response<*>): APIError {
        val apiError = mapResponseBodyToApiError(response)
        if (apiError != null) {
            return apiError
        }
        return APIError(response.raw().code)
    }

    private fun mapResponseBodyToApiError(response: Response<*>): APIError? {
        return try {
            val converter: Converter<ResponseBody?, APIError> =
                apiFactory.buildRetrofit()!!.responseBodyConverter(
                    APIError::class.java, arrayOfNulls<Annotation>(0)
                )
            val error = converter.convert(response.errorBody()!!)
            if (error != null) {
                error.setErrorCode(response.code())
                error
            } else {
                APIError(response.code())
            }
        } catch (e: IOException) {
            null
        }
    }
}
