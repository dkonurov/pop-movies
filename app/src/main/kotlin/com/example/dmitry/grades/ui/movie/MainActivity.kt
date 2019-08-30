package com.example.dmitry.grades.ui.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bottom.navigation.ui.MovieRouter
import com.example.dmitry.grades.R
import com.example.dmitry.grades.ui.movie.details.view.DetailsFragment
import com.example.navigation.BottomNavFragment

class MainActivity : AppCompatActivity(), MovieRouter {

    override fun showDetails(movieId: Long) {
        supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    DetailsFragment.newInstance(movieId),
                    DetailsFragment.javaClass.simpleName
                )
                .addToBackStack(DetailsFragment.javaClass.simpleName)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(
                        R.id.container,
                        BottomNavFragment.newInstance(),
                        BottomNavFragment.javaClass.simpleName
                    )
                    .commit()
        }
    }

    companion object {
        fun makeIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
