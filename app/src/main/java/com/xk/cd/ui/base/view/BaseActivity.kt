package com.xk.cd.ui.base.view

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.xk.cd.common.util.wrapContext
import dagger.android.support.DaggerAppCompatActivity

/**
 * Base activity for all activities
 */
abstract class BaseActivity : DaggerAppCompatActivity() {

    protected var viewDataBinding: ViewDataBinding? = null
        private set

    /**
     * Provides layout id of this view
     */
    @get:LayoutRes
    protected abstract val layoutRId: Int

    /**
     * Handles inflation and view binding
     * Don't forget to call super due to this!
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doInjections()
        preInflate()
        if (layoutRId != 0) {
            viewDataBinding = DataBindingUtil.setContentView(this, layoutRId)
        }
        postInflate(viewDataBinding)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(wrapContext(base))
    }

    /**
     * Invoked before inflating the view
     * Called even when view won't be inflated if layout id is 0
     */
    protected open fun preInflate() {}

    /**
     * Invoked just after inflating the view
     * Called even when view was not inflated if layout id was 0
     * Override to add variables, etc.
     */
    protected open fun postInflate(viewDataBinding: ViewDataBinding?) {}

    /**
     * Override this if you want to inject lifecycle delegates prior to your onCreate() code
     * This will be called only once (in onCreate() method)
     * Don't forget to call super
     */
    protected fun doInjections() {}
}
