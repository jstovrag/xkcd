package com.xk.cd.ui.splash

import androidx.lifecycle.ViewModel
import com.xk.cd.ui.base.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SplashModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun provideSplashViewModel(splashViewModel: SplashViewModel?): ViewModel?
}
