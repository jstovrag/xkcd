package com.xk.cd.common.extensions

import android.content.Context
import android.net.ConnectivityManager

/**
 * Extension functions explicitly for Context
 */

fun Context.hasInternetConnection(): Boolean {
    return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        .activeNetworkInfo?.isConnected == true
}
