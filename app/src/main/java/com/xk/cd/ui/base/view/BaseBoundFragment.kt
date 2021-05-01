package com.xk.cd.ui.base.view

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.xk.cd.R
import com.xk.cd.common.extensions.popBackStack
import com.xk.cd.common.helpers.IOnBackPressed
import com.xk.cd.data.base.error.BaseError
import com.xk.cd.data.base.error.DefaultError
import com.xk.cd.data.base.error.NetworkError
import com.xk.cd.data.base.error.ServerError
import com.xk.cd.ui.base.NavigationCommand.*
import com.xk.cd.ui.base.viewmodel.BaseViewModel
import com.xk.cd.ui.main.MainActivity
import javax.inject.Inject

abstract class BaseBoundFragment<VIEW_MODEL_TYPE : BaseViewModel> : BaseFragment(),
    BoundView<VIEW_MODEL_TYPE>, IOnBackPressed {

    private val navController: NavController?
        get() {
            val activity = activity as AppCompatActivity?
            if (activity != null) {
                var navFragment: Fragment? = null
                when (activity) {
                    is MainActivity -> {
                        navFragment =
                            requireActivity().supportFragmentManager.findFragmentById(R.id.main_navigation_host)
                    }
                }
                if (navFragment != null) {
                    return NavHostFragment.findNavController(navFragment)
                }
            }
            return null
        }

    /**
     * Use this if you need to get activity view model
     * (ViewModelProviders.of(activity, viewModelFactory).get(activityViewModelClass))
     *
     * That way it will use the injected instance of ViewModel
     */
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModelType: VIEW_MODEL_TYPE
    private var progressDialog: ProgressDialog? = null

    override val viewModel: VIEW_MODEL_TYPE
        get() = viewModelType

    override fun postInflate(viewDataBinding: ViewDataBinding?) {
        super.postInflate(viewDataBinding)
        viewModelType =
            ViewModelProvider(this, viewModelFactory).get(viewModelClass as Class<VIEW_MODEL_TYPE>)
        lifecycle.addObserver(viewModelType)
        if (arguments != null) {
            viewModelType.arguments = requireArguments()
        }
        if (viewDataBinding != null) {
            val viewModelRId = viewModelNameRId
            if (viewModelRId != 0) {
                viewDataBinding.run {
                    setVariable(viewModelRId, viewModelType)
                    lifecycleOwner = viewLifecycleOwner
                    executePendingBindings()
                }
            }
        }
        setObservers()
        bindToViewModel()
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    fun pushViewBelowStatusBar(view: View) {
        ViewCompat.requestApplyInsets(requireView())
        ViewCompat.setOnApplyWindowInsetsListener(
            view
        ) { v: View, insets: WindowInsetsCompat ->
            (v.layoutParams as ViewGroup.MarginLayoutParams).topMargin = insets.systemWindowInsetTop
            insets.consumeSystemWindowInsets()
        }
    }

    private fun showProgressDialogActivity() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(activity, R.style.ProgressDialogStyle)
            progressDialog?.run {
                show()
                setContentView(R.layout.progress_dialog)
                setCancelable(false)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    private fun dismissProgressDialogActivity() {
        if (progressDialog != null) {
            if (progressDialog!!.isShowing) progressDialog!!.dismiss()
            progressDialog = null
        }
    }

    private fun navigateTo(directions: NavDirections?) {
        if (navController != null) {
            navController!!.navigate(directions!!)
        }
    }

    /**
     * Attempts to pop the controller's back stack back to a specific destination.
     * If destination is popped and user is navigated to destination true is returned, false otherwise.
     */
    private fun navigateBackTo(directions: NavDirections): Boolean {
        return if (navController != null) {
            navController!!.popBackStack(directions, false)
        } else false
    }

    protected fun navigateUp() {
        if (navController != null) {
            navController!!.navigateUp()
        }
    }

    private fun setObservers() = with(viewModelType) {
        navigationCommands.observe(viewLifecycleOwner) {
            if (it is To) {
                navigateTo(it.directions)
            } else if (it is BackTo) {
                val directions = it.directions
                // If backstack doesn't contain destination, navigate normally to destination
                if (!navigateBackTo(directions)) {
                    navigateTo(directions)
                }
            } else if (it is Back) {
                navigateUp()
            }
        }

        setLoadingObserver()
        setDefaultErrorObserver()
    }

    open fun setLoadingObserver() {
        viewModelType.isLoading.observe(viewLifecycleOwner) {
            if (it) showProgressDialogActivity() else dismissProgressDialogActivity()
        }
    }

    open fun setDefaultErrorObserver() {
        viewModelType.error.observe(viewLifecycleOwner) {
            if (it is BaseError.Default || it is ServerError || it is DefaultError || it is NetworkError) {
                val dialog = DialogViewModel(
                    titleId = it.title,
                    messageId = it.description,
                    positiveButtonListener = { dialogInterface, _ ->
                        dialogInterface.dismiss()
                        navigateUp()
                    }
                )
                dialog.showDialog(requireContext())
            }
        }
    }
}
