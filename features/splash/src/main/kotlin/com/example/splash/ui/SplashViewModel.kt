package com.example.splash.ui

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.base.schedulers.SchedulerProvider
import com.example.base.ui.vm.ErrorViewModel
import com.example.base.ui.vm.SingleLiveEvent
import com.example.core.data.logger.Logger
import com.example.core.network.models.ImageConfigDTO
import com.example.splash.domain.SplashNavigator
import com.example.splash.domain.repositories.configurtaion.BaseConfigRepository
import com.example.storage.preferences.ErrorMessageDataSource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

internal class SplashViewModel @Inject constructor(
    coroutineScope: CoroutineScope,
    schedulerProvider: SchedulerProvider,
    messageDataSource: ErrorMessageDataSource,
    logger: Logger,
    private val baseConfigRepository: BaseConfigRepository,
    private val splashNavigator: SplashNavigator
) : ErrorViewModel(coroutineScope, schedulerProvider, messageDataSource, logger) {

    val imageConfig: LiveData<ImageConfigDTO>
        get() = _imageConfig

    private val _imageConfig = SingleLiveEvent<ImageConfigDTO>()

    fun loadConfig() {
        coroutine {
            _imageConfig.value = baseConfigRepository.getConfiguration()
        }
    }

    fun nextScreen(context: Context) {
        splashNavigator.nextScreen(context)
    }
}