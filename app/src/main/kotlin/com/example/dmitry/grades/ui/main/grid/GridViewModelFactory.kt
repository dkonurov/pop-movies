package com.example.dmitry.grades.ui.main.grid

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.dmitry.grades.di.Scopes
import toothpick.Toothpick
import javax.inject.Inject

class GridViewModelFactory @Inject constructor() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return Toothpick.openScope(Scopes.REMOTE_SCOPE).getInstance(GridViewModel::class.java) as T
    }
}