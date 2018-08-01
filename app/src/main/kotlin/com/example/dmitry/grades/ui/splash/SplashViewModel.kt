package com.example.dmitry.grades.ui.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.dmitry.grades.domain.models.ImageConfig
import com.example.dmitry.grades.domain.repositories.HttpRepository
import com.example.dmitry.grades.domain.repositories.ResourceRepository
import com.example.dmitry.grades.domain.schedulers.SchedulerProvider
import com.example.dmitry.grades.ui.base.BaseViewModel
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val httpRepository: HttpRepository,
                                          private val resourceRepository: ResourceRepository,
                                          private val schedulerProvier: SchedulerProvider) : BaseViewModel() {

    val imageConfig: LiveData<ImageConfig>
        get() = _imageConfig


    private val _imageConfig = MutableLiveData<ImageConfig>()

    fun loadConfig() {
        disposables.add(httpRepository.getConfiguration()
                .subscribeOn(schedulerProvier.io())
                .observeOn(schedulerProvier.ui())
                .doOnSubscribe {
                    _progress.value = true
                }
                .doOnSuccess {
                    _progress.value = false
                }
                .doOnError {
                    _progress.value = false
                }
                .subscribe({
                    _imageConfig.value = it
                }, {
                    _toast.value = resourceRepository.getNetworkError()
                }))
    }

    fun showedToast() {
        _toast.value = null
    }

}