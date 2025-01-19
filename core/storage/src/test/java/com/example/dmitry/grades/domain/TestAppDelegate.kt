package com.example.dmitry.grades.domain

import android.app.Application
import android.content.Context

class TestAppDelegate : Application() {
    companion object {
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}