package com.xk.cd.ui.main.favoritecomics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xk.cd.data.models.comic.Comic
import com.xk.cd.data.store.RealmCache
import com.xk.cd.ui.base.viewmodel.BaseViewModel
import com.xk.cd.ui.base.viewmodel.SingleLiveEvent
import javax.inject.Inject

class FavoriteComicsViewModel @Inject constructor(
    private val comicRealmCache: RealmCache<Comic>
) : BaseViewModel() {

    private val _favoriteComics = SingleLiveEvent<List<Comic>>()
    val favoriteComics: LiveData<List<Comic>>
        get() = _favoriteComics

    private val _isFavoriteComicsListEmpty = MutableLiveData<Boolean>()
    val isFavoriteComicsListEmpty: LiveData<Boolean>
        get() = _isFavoriteComicsListEmpty

    override fun onLifecycleOwnerResume() {
        super.onLifecycleOwnerResume()
        loadFavoriteComics()
    }

    fun goToFavoriteComicDetails(comic: Comic) {
        navigate(
            FavoriteComicsFragmentDirections.actionFavoriteComicsToComicDetails().setComic(comic)
        )
    }

    fun deleteComic(comic: Comic) {
        comicRealmCache.deleteComic(comic)
        loadFavoriteComics()
    }

    fun deleteAllComicsClick() {
        comicRealmCache.deleteAll(Comic::class.java)
        loadFavoriteComics()
    }

    private fun loadFavoriteComics() {
        _favoriteComics.value = comicRealmCache.load(Comic::class.java)
        _isFavoriteComicsListEmpty.value = _favoriteComics.value.isNullOrEmpty()
    }
}
