package com.example.dmitry.grades.ui.movie.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dmitry.grades.di.Scopes
import toothpick.Toothpick
import javax.inject.Inject

class DetailsViewModelFactory @Inject constructor() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return Toothpick.openScope(Scopes.MOVIE_DETAILS_SCOPE).getInstance(DetailsViewModel::class.java) as T
    }
}