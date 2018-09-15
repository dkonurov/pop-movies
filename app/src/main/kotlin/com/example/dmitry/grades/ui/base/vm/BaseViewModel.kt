package com.example.dmitry.grades.ui.base.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
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

    val loading: LiveData<Boolean>
        get() = _loading

    protected val _loading = MutableLiveData<Boolean>()

    protected fun launchJob(context: CoroutineContext = UI,
                            block: suspend () -> Unit): Job {
        return launch(context) {
            try {
                _loading.value = true
                block.invoke()
            } catch (t: Throwable) {
                handleError(t)
            } finally {
                _loading.value = false
            }
        }
    }

    protected abstract fun handleError(t: Throwable)

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