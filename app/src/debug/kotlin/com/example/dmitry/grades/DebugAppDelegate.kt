package com.example.dmitry.grades

import android.app.Application
import android.content.Context

class DebugAppDelegate : Application() {
    companion object {
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}