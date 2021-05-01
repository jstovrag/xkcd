package com.xk.cd.ui.main

import com.xk.cd.ui.base.di.FragmentScope
import com.xk.cd.ui.main.comicdetails.ComicDetailsFragment
import com.xk.cd.ui.main.favoritecomics.FavoriteComicsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilder {

    @FragmentScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun provideMainFragment(): MainFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun provideFavoriteComicsFragment(): FavoriteComicsFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun provideComicDetailsFragment(): ComicDetailsFragment
}
