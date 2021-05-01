package com.xk.cd.common.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.xk.cd.data.base.error.AppException
import com.xk.cd.data.base.error.ErrorUtils
import io.reactivex.Single
import retrofit2.Response
import java.io.IOException
import java.io.InputStream
import java.net.URL


// NavController
fun NavController.popBackStack(directions: NavDirections, inclusive: Boolean): Boolean {
    return currentDestination?.getAction(directions.actionId)?.destinationId?.run {
        popBackStack(this, inclusive)
    } ?: false
}

// Retrofit
fun <T> Response<List<T>>.asBodyList(errorMapper: ErrorUtils): List<T> =
    if (isSuccessful) body() ?: listOf() else throw AppException(errorMapper.parseError(this))

inline fun <reified T> Response<T>.asBody(errorMapper: ErrorUtils): T {
    if (isSuccessful) {
        return if (T::class.java.isInstance(Unit)) {
            Unit as T
        } else
            body() ?: T::class.java.newInstance()
    }
    throw AppException(errorMapper.parseError(this))
}

//fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T) -> Unit) {
//    this.observe(owner, Observer { item ->
//        if (item != null) observer(item)
//    })
//}
//
//fun <T> LiveData<T>.observeNullable(owner: LifecycleOwner, observer: (T?) -> Unit) {
//    this.observe(owner, Observer { item ->
//        observer(item)
//    })
//}

// String
fun String.getBitmapFromURL(): Single<Bitmap?> {
    return Single.fromCallable {
        try {
            val inputStream: InputStream = URL(this).openStream()
            return@fromCallable BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            return@fromCallable null
        }
    }
//
//    return try {
//        val url = URL(this)
//        val connection: HttpURLConnection = url
//            .openConnection() as HttpURLConnection
//        connection.doInput = true
//        connection.connect()
//        val input: InputStream = connection.inputStream
//        BitmapFactory.decodeStream(input)
//    } catch (e: IOException) {
//        e.printStackTrace()
//        null
//    }
}
