package com.example.splash.ui

sealed interface SideEffects {
    data object NextScreen : SideEffects
}