package com.xk.cd.ui.base.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.xk.cd.R
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    /**
     * Provides layout id of this view
     * May not be 0!
     */
    @get:LayoutRes
    protected abstract val layoutRId: Int

    protected lateinit var viewDataBinding: ViewDataBinding
        private set

    /**
     * Inflates layout
     * Don't forget to call super due to this
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preInflate()
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutRId, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postInflate(viewDataBinding)
    }

    override fun onCreateAnimation(
        transit: Int,
        enter: Boolean,
        nextAnim: Int
    ): Animation? {
        if (nextAnim == R.anim.nav_default_enter_anim) {
            val nextAnimation =
                AnimationUtils.loadAnimation(context, nextAnim)
            nextAnimation.setAnimationListener(object : Animation.AnimationListener {
                private var startZ = 0f
                override fun onAnimationStart(animation: Animation) {
                    if (view != null) {
                        startZ = ViewCompat.getTranslationZ(view!!)
                        ViewCompat.setTranslationZ(view!!, 1f)
                    }
                }

                override fun onAnimationEnd(animation: Animation) {
                    if (view != null) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            ViewCompat.setTranslationZ(
                                view!!,
                                startZ
                            )
                        }, 100)
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            return nextAnimation
        }
        return null
    }

    /**
     * Invoked before inflating the view
     */
    protected open fun preInflate() {}

    /**
     * Invoked just after inflating the view
     * Override to add variables, etc.
     */
    protected open fun postInflate(viewDataBinding: ViewDataBinding?) {}
}
