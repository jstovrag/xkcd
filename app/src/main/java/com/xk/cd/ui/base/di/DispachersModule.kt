package com.xk.cd.ui.base.di

import com.xk.cd.ui.base.DispachersProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
object DispachersModule {

    @Provides
    @JvmStatic
    fun provideViewModelScope(): CoroutineScope
            = CoroutineScope(Dispatchers.Main + SupervisorJob(null))

    @Singleton
    @Provides
    @JvmStatic
    fun provideDispachers(): DispachersProvider = object : DispachersProvider {
        override fun io(): CoroutineDispatcher = Dispatchers.IO

        override fun main(): CoroutineDispatcher = Dispatchers.Main

        override fun computation(): CoroutineDispatcher = Dispatchers.Default

        override fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
    }
}
