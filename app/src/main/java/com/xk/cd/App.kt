package com.xk.cd

import android.app.Activity
import android.content.Context
import com.xk.cd.ui.base.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class App : DaggerApplication() {

    @JvmField
    @Inject
    var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null

    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    override fun applicationInjector(): AndroidInjector<App> {
        return DaggerAppComponent.builder().create(this)
    }
}
