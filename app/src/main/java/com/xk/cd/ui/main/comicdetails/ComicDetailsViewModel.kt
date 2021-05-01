package com.xk.cd.ui.main.comicdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xk.cd.data.models.comic.Comic
import com.xk.cd.data.store.RealmCache
import com.xk.cd.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class ComicDetailsViewModel @Inject constructor(
    private val comicRealmCache: RealmCache<Comic>
) : BaseViewModel() {

    private val _comic = MutableLiveData<Comic>()
    val comic: LiveData<Comic>
        get() = _comic

    private val _comicDeleted = MutableLiveData<Boolean>()
    val comicDeleted: LiveData<Boolean>
        get() = _comicDeleted

    override fun onLifecycleOwnerResume() {
        super.onLifecycleOwnerResume()
        _comic.value = ComicDetailsFragmentArgs.fromBundle(arguments).comic
    }

    fun deleteComicClick() {
        comicRealmCache.deleteComic(_comic.value!!)
        _comicDeleted.value = true
    }
}
