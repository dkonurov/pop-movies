package com.example.splash.ui

sealed class State {
    object Empty : State()
    object Loading : State()
    object Success : State()
    data class Error(val throwable: Throwable?) : State()
}