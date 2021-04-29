package com.xk.cd.common.helpers

import android.content.Context
import com.xk.cd.common.extensions.hasInternetConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHelper @Inject constructor(val context: Context) {

    fun hasInternetConnection(): Boolean {
        return context.hasInternetConnection()
    }
}
