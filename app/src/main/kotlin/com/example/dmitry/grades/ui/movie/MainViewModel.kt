package com.example.dmitry.grades.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.navigation.MovieDetailsRouter
import javax.inject.Inject

class MainViewModel @Inject constructor(
    router: MovieDetailsRouter
) : ViewModel() {

    val screens: LiveData<Long> = router.screens
}