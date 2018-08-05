package com.example.dmitry.grades.ui.movie.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.dmitry.grades.di.Scopes
import toothpick.Toothpick
import javax.inject.Inject

class ListViewModelFactory @Inject constructor() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return Toothpick.openScope(Scopes.REMOTE_SCOPE).getInstance(ListViewModel::class.java) as T
    }
}