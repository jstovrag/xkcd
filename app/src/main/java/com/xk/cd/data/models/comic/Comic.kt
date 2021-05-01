package com.xk.cd.data.models.comic

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.ByteArrayOutputStream
import java.io.Serializable

open class Comic(
    @PrimaryKey
    var num: Int = 0,
    var title: String? = null,
    var month: String? = null,
    var year: String? = null,
    var day: String? = null,
    var link: String? = null,
    var news: String? = null,
    @SerializedName("safe_title")
    var safeTitle: String? = null,
    var transcript: String? = null,
    var alt: String? = null,
    var img: String? = null,
    var comicImage: ByteArray? = null
) : RealmObject(), Serializable {

    fun setComicImage(bitmap: Bitmap?) {
        comicImage = if (bitmap != null) bitmapToByteArray(bitmap) else bitmap
    }

    fun getComicImage(): Bitmap? {
        return if (comicImage != null) {
            BitmapFactory.decodeByteArray(comicImage, 0, comicImage!!.size)
        } else null
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        return stream.toByteArray()
    }
}
