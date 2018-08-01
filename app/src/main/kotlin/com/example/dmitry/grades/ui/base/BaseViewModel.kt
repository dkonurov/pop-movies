package com.example.dmitry.grades.ui.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()

    val toast: LiveData<String>
        get() = _toast

    protected val _toast = MutableLiveData<String>()

    val progress: LiveData<Boolean>
        get() = _progress

    protected val _progress = MutableLiveData<Boolean>()


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}