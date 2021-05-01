package com.xk.cd.ui.base.di.module

import com.xk.cd.ui.base.di.ActivityScope
import com.xk.cd.ui.main.MainActivity
import com.xk.cd.ui.main.MainFragmentBuilder
import com.xk.cd.ui.main.MainModule
import com.xk.cd.ui.splash.SplashActivity
import com.xk.cd.ui.splash.SplashModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Activities and their components should be provided in this file
 *
 *
 * This may be further modularized, but all such modules should be added to the [com.xk.cd.ui.base.di.component.AppComponent]
 */
@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun provideSplashActivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class, MainFragmentBuilder::class])
    abstract fun provideMainActivity(): MainActivity
}
