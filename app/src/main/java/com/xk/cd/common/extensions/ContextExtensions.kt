package com.xk.cd.common.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater

/**
 * Extension functions explicitly for Context
 */

fun Context.hasInternetConnection(): Boolean {
    return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        .activeNetworkInfo?.isConnected == true
}

fun Context.getLayoutInflater() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
