package com.example.dmitry.grades.domain.navigators

import android.content.Context
import com.example.dmitry.grades.ui.movie.MainActivity
import com.example.splash.domain.SplashNavigator
import javax.inject.Inject

class SplashNavigatorImpl @Inject constructor() : SplashNavigator {

    override fun nextScreen(context: Context) {
        val intent = MainActivity.makeIntent(context)
        context.startActivity(intent)
    }
}