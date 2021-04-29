package com.xk.cd.ui.base

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object BindingAdapters {
    @BindingAdapter("app:visible")
    @JvmStatic
    fun isVisible(view: View, visible: Boolean?) {
        view.visibility = if (visible == true) View.VISIBLE else View.GONE
    }

    @BindingAdapter("app:visibleOrInvisible")
    @JvmStatic
    fun isVisibleOrInvisible(view: View, visible: Boolean?) {
        view.visibility = if (visible == true) View.VISIBLE else View.INVISIBLE
    }

    @BindingAdapter("android:text")
    @JvmStatic
    fun setText(textView: TextView, resId: Int) {
        if (resId != 0) textView.text = textView.context.resources.getString(resId)
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun loadImage(view: ImageView, url: String?) {
        url?.let {
            Picasso.get()
                .load(url)
                .into(view)
        }
    }
}
