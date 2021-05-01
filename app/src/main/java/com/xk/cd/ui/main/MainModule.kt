package com.xk.cd.ui.main

import androidx.lifecycle.ViewModel
import com.xk.cd.ui.base.di.viewmodel.ViewModelKey
import com.xk.cd.ui.main.comicdetails.ComicDetailsViewModel
import com.xk.cd.ui.main.favoritecomics.FavoriteComicsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainFragmentViewModel::class)
    abstract fun provideMainFragmentViewModel(mainFragmentViewModel: MainFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteComicsViewModel::class)
    abstract fun provideFavoriteComicsViewModel(favoriteComicsViewModel: FavoriteComicsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ComicDetailsViewModel::class)
    abstract fun provideComicDetailsViewModel(comicDetailsViewModel: ComicDetailsViewModel): ViewModel
}
