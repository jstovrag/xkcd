package com.xk.cd.ui.main.favoritecomics

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.xk.cd.BR
import com.xk.cd.R
import com.xk.cd.databinding.FragmentFavoriteComicsBinding
import com.xk.cd.ui.base.view.BaseBoundFragment


class FavoriteComicsFragment : BaseBoundFragment<FavoriteComicsViewModel>() {

    lateinit var binding: FragmentFavoriteComicsBinding
    private val favoriteComicsAdapter by lazy {
        FavoriteComicsAdapter(
            viewModel::goToFavoriteComicDetails,
            viewModel::deleteComic
        )
    }

    override val layoutRId = R.layout.fragment_favorite_comics
    override val viewModelNameRId = BR.viewModel
    override val viewModelClass = FavoriteComicsViewModel::class.java

    override fun bindToViewModel() {
        binding = viewDataBinding as FragmentFavoriteComicsBinding
        pushViewBelowStatusBar(binding.container)

        setFavoriteComicsAdapter()
        setObservers()
    }

    private fun setObservers() = with(viewModel) {
        favoriteComics.observe(viewLifecycleOwner) {
            favoriteComicsAdapter.setData(it)
        }
    }

    private fun setFavoriteComicsAdapter() {
        binding.recyclerFavoriteComicsList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = favoriteComicsAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}
