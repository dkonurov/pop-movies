package com.example.splash.ui

internal sealed interface State {
    data object Loading : State
    data class Error(val throwable: Throwable) : State
}