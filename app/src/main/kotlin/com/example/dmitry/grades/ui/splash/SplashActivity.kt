package com.example.dmitry.grades.ui.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.example.dmitry.grades.R
import com.example.dmitry.grades.di.Scopes
import com.example.dmitry.grades.ui.main.MainActivity
import toothpick.Toothpick

class SplashActivity : AppCompatActivity() {

    private lateinit var splashViewModel: SplashViewModel

    val factory = Toothpick.openScope(Scopes.REMOTE_SCOPE).getInstance(SplashViewModelFactory::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashViewModel = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)
        val progress = findViewById<FrameLayout>(R.id.progress)
        splashViewModel.toast.observe(this, Observer {
            it?.let {
                Toast.makeText(this@SplashActivity, it, Toast.LENGTH_SHORT).show()
                splashViewModel.showedToast()
            }
        })
        splashViewModel.loading.observe(this, Observer {
            it?.let {
                if (it) {
                    if (progress.visibility != View.VISIBLE) {
                        progress.visibility = View.VISIBLE
                    }
                } else {
                    if (progress.visibility != View.GONE) {
                        progress.visibility = View.GONE
                    }
                }
            }
        })
        splashViewModel.imageConfig.observe(this, Observer {
            startActivity(MainActivity.makeIntent(this@SplashActivity))
            finish()
        })

        if (savedInstanceState == null) {
            splashViewModel.loadConfig()
        }
    }
}