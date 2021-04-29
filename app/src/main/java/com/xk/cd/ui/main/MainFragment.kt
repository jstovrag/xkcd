package com.xk.cd.ui.main

import com.xk.cd.BR
import com.xk.cd.R
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
    }
}
