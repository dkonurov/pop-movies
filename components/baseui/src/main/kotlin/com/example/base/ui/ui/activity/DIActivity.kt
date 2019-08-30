package com.example.base.ui.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class DIActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            createScope()
        }
    }

    protected open fun createScope() {
        //do nothing
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            closeScope()
        }
    }

    protected open fun closeScope() {
        //do nothing
    }

}