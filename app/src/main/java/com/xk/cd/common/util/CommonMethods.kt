package com.xk.cd.common.util

import android.content.Context
import android.content.res.Configuration
import java.util.*

fun wrapContext(context: Context): Context {
    val newConfig = Configuration().apply {
        setLocale(Locale.getDefault())
    }
    return context.createConfigurationContext(newConfig)
}
