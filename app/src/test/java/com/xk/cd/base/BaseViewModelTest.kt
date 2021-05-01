package com.xk.cd.base

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.xk.cd.common.helpers.NetworkHelper
import com.xk.cd.data.base.error.APIError
import com.xk.cd.data.base.error.BaseError
import com.xk.cd.ui.base.NavigationCommand
import com.xk.cd.ui.base.viewmodel.BaseViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

abstract class BaseViewModelTest {

    /**
     * This rule forces LiveData updates to happen on calling thread
     */
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @MockK
    lateinit var errorObserver: Observer<BaseError?>

    @MockK
    lateinit var navigationObserver: Observer<NavigationCommand>

    @MockK
    lateinit var loadingObserver: Observer<Boolean>

    @MockK
    lateinit var networkHelper: NetworkHelper

    @MockK
    lateinit var arguments: Bundle

    @MockK
    lateinit var apiError: APIError

    @Before
    open fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { networkHelper.hasInternetConnection() } returns true
    }

    protected fun commonSetup(viewModel: BaseViewModel) {
        viewModel.networkHelper = networkHelper
    }
}

@ExperimentalCoroutinesApi
class CoroutineTestRule : TestRule {
    override fun apply(base: Statement?, description: Description?): Statement =
        object : Statement() {
            override fun evaluate() {
                Dispatchers.setMain(TestCoroutineDispatcher())
                try {
                    base?.evaluate()
                } finally {
                    Dispatchers.resetMain()
                }
            }
        }
}

fun LifecycleRegistry.dispatchEvent(viewModel: BaseViewModel, lifecycleEvent: Lifecycle.Event) {
    addObserver(viewModel)
    handleLifecycleEvent(lifecycleEvent)
}
