package com.example.dmitry.grades.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.dmitry.grades.R
import com.example.dmitry.grades.ui.main.grid.GridFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, GridFragment.newInstance())
                    .commit()
        }
    }

    companion object {
        fun makeIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
