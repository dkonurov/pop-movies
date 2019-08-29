package com.example.base.ui.vm

import androidx.lifecycle.ViewModel
import com.example.base.schedulers.SchedulerProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel
constructor(
    private val scope: CoroutineScope,
    protected val schedulerProvider: SchedulerProvider
) : ViewModel() {

    protected abstract fun handleError(t: Throwable)

    protected abstract fun hideError()

    protected abstract fun showLoading()

    protected abstract fun hideLoading()

    protected fun coroutine(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return scope.launch(context, start) {
            try {
                showLoading()
                hideError()
                block.invoke(this)
            } catch (t: Throwable) {
                handleError(t)
            } finally {
                hideLoading()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }
}