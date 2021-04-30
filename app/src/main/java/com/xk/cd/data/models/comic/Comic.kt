package com.xk.cd.data.models.comic

import com.google.gson.annotations.SerializedName

data class Comic(
    val num: Int,
    val title: String,
    val month: String,
    val year: String,
    val day: String,
    val link: String,
    val news: String,
    @SerializedName("safe_title")
    val safeTitle: String,
    val transcript: String,
    val alt: String,
    val img: String
)
