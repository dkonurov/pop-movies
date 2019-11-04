package com.example.dmitry.grades.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dmitry.grades.di.Scopes
import toothpick.Toothpick
import javax.inject.Inject

class ApplicationScopeFactory @Inject constructor() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return Toothpick.openScope(Scopes.REMOTE_SCOPE).getInstance(modelClass)
    }
}