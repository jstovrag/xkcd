package com.xk.cd.ui.main.comicdetails

import com.xk.cd.BR
import com.xk.cd.R
import com.xk.cd.databinding.FragmentComicDetailsBinding
import com.xk.cd.ui.base.view.BaseBoundFragment

class ComicDetailsFragment : BaseBoundFragment<ComicDetailsViewModel>() {

    lateinit var binding: FragmentComicDetailsBinding

    override val layoutRId = R.layout.fragment_comic_details
    override val viewModelNameRId = BR.viewModel
    override val viewModelClass = ComicDetailsViewModel::class.java

    override fun bindToViewModel() {
        binding = viewDataBinding as FragmentComicDetailsBinding
        pushViewBelowStatusBar(binding.container)

        setObservers()
    }

    private fun setObservers() = with(viewModel) {
        comicDeleted.observe(viewLifecycleOwner) {
            navigateUp()
        }
    }
}
