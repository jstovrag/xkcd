package com.xk.cd.ui.main

import com.xk.cd.BR
import com.xk.cd.R
import com.xk.cd.ui.base.view.BaseBoundActivity
import com.xk.cd.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class MainActivity : BaseBoundActivity<MainViewModel>() {

    override val layoutRId = R.layout.activity_main
    override val viewModelNameRId = BR.viewModel
    override val viewModelClass = MainViewModel::class.java

    override fun bindToViewModel() {
    }
}

class MainViewModel @Inject constructor() : BaseViewModel()

