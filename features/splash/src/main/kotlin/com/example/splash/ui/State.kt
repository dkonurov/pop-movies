package com.example.splash.ui

import com.example.core.model.PresentationError

internal sealed interface State {
    data object Loading : State
    data class Error(val present: PresentationError) : State
}