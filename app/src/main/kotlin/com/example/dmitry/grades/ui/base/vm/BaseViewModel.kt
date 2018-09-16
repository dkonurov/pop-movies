package com.example.dmitry.grades.ui.base.vm

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    protected val jobs = mutableListOf<Job>()

    protected val disposables = CompositeDisposable()

    protected fun cancelable(runnable: () -> Job) {
        jobs.filter { it.isCancelled || it.isCompleted }
                .forEach { jobs.remove(it) }
        jobs.add(runnable.invoke())
    }

    protected fun launchJob(context: CoroutineContext = UI,
                            block: suspend () -> Unit): Job {
        return launch(context) {
            try {
                showLoading()
                hideError()
                block.invoke()
            } catch (t: Throwable) {
                handleError(t)
            } finally {
                hideLoading()
            }
        }
    }

    protected abstract fun handleError(t: Throwable)

    protected abstract fun hideError()

    protected abstract fun showLoading()

    protected abstract fun hideLoading()

    protected fun coroutine(context: CoroutineContext = UI,
                            block: suspend () -> Unit) {
        cancelable {
            launchJob(context, block)
        }
    }


    override fun onCleared() {
        super.onCleared()
        jobs.forEach { it.cancel() }
        jobs.clear()
    }
}