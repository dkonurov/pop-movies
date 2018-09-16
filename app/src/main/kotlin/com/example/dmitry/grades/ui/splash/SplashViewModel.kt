package com.example.dmitry.grades.ui.splash

import android.arch.lifecycle.LiveData
import com.example.dmitry.grades.domain.Logger
import com.example.dmitry.grades.domain.models.ImageConfig
import com.example.dmitry.grades.domain.repositories.ResourceRepository
import com.example.dmitry.grades.domain.repositories.configurtaion.BaseConfigRepository
import com.example.dmitry.grades.ui.base.vm.ErrorViewModel
import com.example.dmitry.grades.ui.base.vm.SingleLiveEvent
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val baseConfigRepository: BaseConfigRepository,
                                          resourceRepository: ResourceRepository,
                                          logger: Logger) : ErrorViewModel(resourceRepository, logger) {

    val imageConfig: LiveData<ImageConfig>
        get() = _imageConfig


    private val _imageConfig = SingleLiveEvent<ImageConfig>()

    fun loadConfig() {
        coroutine {
            _imageConfig.value = baseConfigRepository.getConfiguration()
        }
    }
}

