package com.xk.cd.ui.base.view

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.xk.cd.ui.base.viewmodel.BaseViewModel
import com.xk.cd.R
import javax.inject.Inject

abstract class BaseBoundActivity<VIEW_MODEL_TYPE : BaseViewModel> : BaseActivity(),
    BoundView<VIEW_MODEL_TYPE> {

    private var progressDialog: ProgressDialog? = null
    private lateinit var viewModelType: VIEW_MODEL_TYPE

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    final override lateinit var viewModel: VIEW_MODEL_TYPE

    /**
     * Prepares viewmodel before inflating
     * Don't forget to call super due to this!
     */
    override fun preInflate() {
        super.preInflate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        viewModelType =
            ViewModelProvider(
                this,
                viewModelFactory
            ).get(viewModelClass as Class<VIEW_MODEL_TYPE>)
        lifecycle.addObserver(viewModelType)
    }

    /**
     * Binds viewmodel after inflating
     * Don't forget to call super due to this!
     */
    override fun postInflate(viewDataBinding: ViewDataBinding?) {
        super.postInflate(viewDataBinding)
        if (viewDataBinding != null) {
            val viewModelRId = viewModelNameRId
            if (viewModelRId != 0) {
                viewDataBinding.run {
                    setVariable(viewModelRId, viewModelType)
                    lifecycleOwner = this@BaseBoundActivity
                    executePendingBindings()
                }
            }
        }
        bindToViewModel()
    }

    fun showProgressDialogActivity() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this@BaseBoundActivity, R.style.ProgressDialogStyle)
            progressDialog?.run {
                show()
                setContentView(R.layout.progress_dialog)
                setCancelable(false)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    fun dismissProgressDialogActivity() {
        if (progressDialog != null) {
            if (progressDialog!!.isShowing) progressDialog!!.dismiss()
            progressDialog = null
        }
    }
}
