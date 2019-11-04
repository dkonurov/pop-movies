package com.example.splash.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import toothpick.Toothpick
import javax.inject.Inject

internal class SplashScopeFactory @Inject constructor() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return Toothpick.openScope(SplashScope.SPLASH_SCOPE).getInstance(modelClass)
    }
}