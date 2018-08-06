package com.example.dmitry.grades

import android.content.Context

class DebugAppDelegate : AppDelegate() {
    companion object {
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}