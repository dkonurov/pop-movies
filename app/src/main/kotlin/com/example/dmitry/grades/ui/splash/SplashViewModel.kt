package com.example.dmitry.grades.ui.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.dmitry.grades.domain.models.ImageConfig
import com.example.dmitry.grades.domain.repositories.ResourceRepository
import com.example.dmitry.grades.domain.repositories.configurtaion.BaseConfigRepository
import com.example.dmitry.grades.domain.repositories.configurtaion.BaseConfigRepositoryImpl
import com.example.dmitry.grades.domain.schedulers.SchedulerProvider
import com.example.dmitry.grades.ui.base.BaseViewModel
import com.example.dmitry.grades.ui.base.async
import com.example.dmitry.grades.ui.base.loading
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val baseConfigRepository: BaseConfigRepository,
                                          private val resourceRepository: ResourceRepository,
                                          private val schedulerProvier: SchedulerProvider) : BaseViewModel() {

    val imageConfig: LiveData<ImageConfig>
        get() = _imageConfig


    private val _imageConfig = MutableLiveData<ImageConfig>()

    fun loadConfig() {
        disposables.add(baseConfigRepository.getConfiguration()
                .async(schedulerProvier)
                .loading {
                    _loading.value = it
                }
                .subscribe({
                    _imageConfig.value = it
                }, {
                    _toast.value = resourceRepository.getNetworkError()
                }))
    }

}