package com.xk.cd.ui.base.di.component

import com.xk.cd.App
import com.xk.cd.data.comic.ComicDataModule
import com.xk.cd.data.di.module.CacheModule
import com.xk.cd.data.di.module.NetworkModule
import com.xk.cd.scheduling.AndroidSchedulingModule
import com.xk.cd.ui.base.di.module.ActivityBuilder
import com.xk.cd.ui.base.di.module.AppModule
import com.xk.cd.ui.base.di.viewmodel.ViewModelFactoryModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        // Android
        AndroidSupportInjectionModule::class,
        AndroidSchedulingModule::class,

        // Application
        AppModule::class,
        ActivityBuilder::class,
        ViewModelFactoryModule::class,

        // Data
        CacheModule::class,
        NetworkModule::class,
        ComicDataModule::class
    ]
)

@Singleton
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}
