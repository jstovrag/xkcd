package com.xk.cd.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.xk.cd.App
import com.xk.cd.BR
import com.xk.cd.R
import com.xk.cd.common.helpers.RealmHelper
import com.xk.cd.ui.base.view.BaseBoundActivity
import com.xk.cd.ui.base.viewmodel.BaseViewModel
import com.xk.cd.ui.main.MainActivity
import javax.inject.Inject

class SplashActivity : BaseBoundActivity<SplashViewModel>() {

    override val layoutRId = R.layout.activity_splash
    override val viewModelNameRId = BR.viewModel
    override val viewModelClass = SplashViewModel::class.java

    override fun bindToViewModel() {
        RealmHelper.init(App.appContext)
        Handler(Looper.getMainLooper()).postDelayed({
            goToMainActivity()
        }, 3000)
    }

    private fun goToMainActivity() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
}

class SplashViewModel @Inject constructor() : BaseViewModel()
