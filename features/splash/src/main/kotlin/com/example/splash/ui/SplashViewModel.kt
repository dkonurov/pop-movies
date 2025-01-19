package com.example.splash.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.schedulers.DispatcherProvider
import com.example.core.coroutine.resultOf
import com.example.core.mapper.ErrorMapper
import com.example.splash.domain.repositories.configurtaion.RequestConfigurationUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SplashViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val requestConfigurationUseCase: RequestConfigurationUseCase,
    private val errorMapper: ErrorMapper
) : ViewModel() {

    var state by mutableStateOf<State>(value = State.Loading)
        private set

    private val mutableSideEffects = MutableSharedFlow<SideEffects>()

    val sideEffect: Flow<SideEffects> = mutableSideEffects


    fun loadConfig() {
        viewModelScope.launch {
            state = State.Loading
            resultOf {
                withContext(dispatcherProvider.io()) {
                    requestConfigurationUseCase.execute()
                }
                mutableSideEffects.emit(SideEffects.NextScreen)
            }.onFailure { State.Error(errorMapper.map(it)) }
        }
    }
}