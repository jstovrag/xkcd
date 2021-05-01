package com.xk.cd.base

import androidx.lifecycle.Observer

class TestObserver<T> : Observer<T> {

    private var data: T? = null
    private var times: Int = 0

    override fun onChanged(t: T?) {
        data = t
        times += 1
    }

    fun getValue(): T? = data

    fun getTimes(): Int = times
}
