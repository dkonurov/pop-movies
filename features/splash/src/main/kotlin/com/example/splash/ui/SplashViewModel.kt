package com.example.splash.ui

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.schedulers.SchedulerProvider
import com.example.splash.domain.SplashNavigator
import com.example.splash.domain.repositories.configurtaion.BaseConfigRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SplashViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val baseConfigRepository: BaseConfigRepository,
    private val splashNavigator: SplashNavigator
) : ViewModel() {

    var state by mutableStateOf<State>(value = State.Empty)
        private set

    fun loadConfig() {
        viewModelScope.launch {
            state = State.Loading
            val result =
                withContext(schedulerProvider.io()) { baseConfigRepository.getConfiguration() }
            state = if (result.isSuccess) {
                State.Success
            } else {
                State.Error(result.exceptionOrNull())
            }
        }
    }

    fun nextScreen(context: Context) {
        splashNavigator.nextScreen(context)
    }
}