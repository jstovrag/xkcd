package com.xk.cd.ui.main

import android.content.Intent
import com.xk.cd.BR
import com.xk.cd.R
import com.xk.cd.common.extensions.observe
import com.xk.cd.databinding.FragmentMainBinding
import com.xk.cd.ui.base.view.BaseBoundFragment

class MainFragment : BaseBoundFragment<MainFragmentViewModel>() {

    lateinit var binding: FragmentMainBinding

    override val layoutRId = R.layout.fragment_main
    override val viewModelNameRId = BR.viewModel
    override val viewModelClass = MainFragmentViewModel::class.java

    override fun bindToViewModel() {
        binding = viewDataBinding as FragmentMainBinding
        pushViewBelowStatusBar(binding.container)

        setObservers()
    }

    private fun setObservers() = with(viewModel) {
        comicDirectUrl.observe(viewLifecycleOwner) {
            shareComic(it)
        }
    }

    private fun shareComic(comicDirectUrl: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, comicDirectUrl)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
