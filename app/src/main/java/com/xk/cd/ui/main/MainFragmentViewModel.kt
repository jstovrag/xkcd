package com.xk.cd.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xk.cd.data.comic.ComicRepository
import com.xk.cd.data.models.comic.Comic
import com.xk.cd.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject
import kotlin.random.Random.Default.nextInt

class MainFragmentViewModel @Inject constructor(
    private val comicRepository: ComicRepository
) : BaseViewModel() {

    private var currentComicNum = 1
    private var lastComicNum = 1

    private val _currentComic = MutableLiveData<Comic>()
    val currentComic: LiveData<Comic>
        get() = _currentComic

    private val _comicDirectUrl = MutableLiveData<String>()
    val comicDirectUrl: LiveData<String>
        get() = _comicDirectUrl

    init {
        getLastComic()

        setErrorHandler {

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

    private fun getComic(num: Int) {
        launch {
            isLoading(true)
            _currentComic.value = comicRepository.getComic(num)
            currentComicNum = _currentComic.value!!.num
        }
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
        getComic(nextInt(1, lastComicNum))
    }

    fun shareComicClick() {
        _comicDirectUrl.value = _currentComic.value?.img
    }
}
