package com.xk.cd.scheduling

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
object AndroidSchedulingModule {

    @Singleton
    @Provides
    fun provideAndroidSchedulingProvider(): SchedulingProvider = object : SchedulingProvider {
        override fun io(): Scheduler = Schedulers.io()

        override fun main(): Scheduler = AndroidSchedulers.mainThread()

        override fun computation(): Scheduler = Schedulers.computation()

        override fun single(): Scheduler = Schedulers.single()
    }
}
