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

    val loading: LiveData<Boolean>
        get() = _loading

    protected val _loading = MutableLiveData<Boolean>()


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun showedToast() {
        _toast.value = null
    }
}