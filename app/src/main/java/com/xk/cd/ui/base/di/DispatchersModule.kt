package com.xk.cd.ui.base.di

import com.xk.cd.ui.base.DispatchersProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
object DispatchersModule {

    @Provides
    fun provideViewModelScope(): CoroutineScope
            = CoroutineScope(Dispatchers.Main + SupervisorJob(null))

    @Singleton
    @Provides
    fun provideDispatchers(): DispatchersProvider = object : DispatchersProvider {
        override fun io(): CoroutineDispatcher = Dispatchers.IO

        override fun main(): CoroutineDispatcher = Dispatchers.Main

        override fun computation(): CoroutineDispatcher = Dispatchers.Default

        override fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
    }
}
