package com.example.dmitry.grades.ui.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.base.extensions.viewModel
import com.example.base.ui.ui.activity.DIActivity
import com.example.details.view.DetailsFragment
import com.example.dmitry.grades.R
import com.example.navigation.BottomNavFragment
import toothpick.config.Module

class MainActivity : DIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(
                    R.id.container,
                    BottomNavFragment.newInstance(),
                    BottomNavFragment::class.java.simpleName,
                )
            }
        }

        val viewModel = viewModel { getScope().getInstance(MainViewModel::class.java) }
        viewModel.screens.observe(
            this,
            {
                supportFragmentManager.commit {
                    replace(R.id.container, DetailsFragment.newInstance(it))
                    addToBackStack(DetailsFragment::class.java.simpleName)
                }
            },
        )
    }

    override fun getModules(): Array<Module>? = arrayOf(MainModule())

    companion object {
        fun makeIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }
}