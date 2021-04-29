package com.xk.cd.data.base.error

import com.xk.cd.R

sealed class BaseError(val title: Int, val description: Int) {
    abstract class FeatureError(title: Int, description: Int = 0) : BaseError(title, description)
    abstract class Default(title: Int, description: Int) : BaseError(title, description)
}

object NetworkError : BaseError(R.string.network_error_title, R.string.network_error_description)
object ServerError : BaseError(R.string.default_error_title, R.string.err_technical)
object DefaultError : BaseError(R.string.default_error_message, R.string.err_technical)
