package com.xk.cd.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xk.cd.App
import com.xk.cd.BuildConfig
import com.xk.cd.common.constants.AppConstants.NEWSLETTER_URL
import com.xk.cd.common.extensions.getBitmapFromURL
import com.xk.cd.common.helpers.RealmHelper
import com.xk.cd.data.comic.ComicRepository
import com.xk.cd.data.models.comic.Comic
import com.xk.cd.data.store.RealmCache
import com.xk.cd.scheduling.SchedulingProvider
import com.xk.cd.ui.base.viewmodel.BaseViewModel
import com.xk.cd.ui.base.viewmodel.SingleLiveEvent
import javax.inject.Inject
import kotlin.random.Random.Default.nextInt


class MainFragmentViewModel @Inject constructor(
    private val comicRepository: ComicRepository,
    private val comicRealmCache: RealmCache<Comic>,
    schedulingProvider: SchedulingProvider
) : BaseViewModel(schedulingProvider) {

    private var currentComicNum = 1
    private var lastComicNum = 1

    private val _currentComic = MutableLiveData<Comic>()
    val currentComic: LiveData<Comic>
        get() = _currentComic

    private val _comicDirectUrl = MutableLiveData<String>()
    val comicDirectUrl: LiveData<String>
        get() = _comicDirectUrl

    private val _openLink = SingleLiveEvent<String>()
    val openLink: LiveData<String>
        get() = _openLink

    private val _comicAddedToFavoriteList = SingleLiveEvent<Unit>()
    val comicAddedToFavoriteList: LiveData<Unit>
        get() = _comicAddedToFavoriteList

    init {
        getLastComic()
        RealmHelper.init(App.appContext)
    }

    fun navFirstClick() {
        getComic(1)
    }

    fun navPrevClick() {
        currentComicNum--
        if (currentComicNum < 1) {
            currentComicNum = 1
        }
        getComic(currentComicNum)
    }

    fun navNextClick() {
        currentComicNum++
        if (currentComicNum > lastComicNum) {
            currentComicNum = lastComicNum
        }
        getComic(currentComicNum)
    }

    fun navLastClick() {
        getLastComic()
    }

    fun randomClick() {
        if (lastComicNum <= 1) {
            getLastComic()
        } else {
            getComic(nextInt(1, lastComicNum))
        }
    }

    fun newsLetterClick() {
        _openLink.value = NEWSLETTER_URL
    }

    fun shareComicClick() {
        _comicDirectUrl.value = _currentComic.value?.img
    }

    fun favoriteClick() {
        if (_currentComic.value != null && networkHelper.hasInternetConnection()) {
            isLoading(true)
            _currentComic.value?.img?.getBitmapFromURL()?.asIOCall()?.subscribe { it ->
                _currentComic.value?.setComicImage(it)
                comicRealmCache.updateOrCreate(_currentComic.value!!)
                _comicAddedToFavoriteList.call()
                isLoading(false)
            }?.disposeOnClear()
        }
    }

    fun favoriteComicsClick() {
        navigate(MainFragmentDirections.actionMainToFavoriteComics())
    }

    fun getComic(num: Int) {
        if (num !in 1..lastComicNum) return

        launch {
            isLoading(true)
            _currentComic.value = comicRepository.getComic(num)
            currentComicNum = _currentComic.value!!.num
        }
    }

    private fun getLastComic() {
        launch {
            isLoading(true)
            _currentComic.value = comicRepository.getLastComic()
            lastComicNum = _currentComic.value!!.num
            currentComicNum = _currentComic.value!!.num
        }
    }
}
