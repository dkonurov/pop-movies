package com.example.splash.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.base.ui.observers.LoadingObserver
import com.example.base.ui.ui.errors.ErrorHandler
import com.example.base.ui.ui.errors.LoadingView
import com.example.base.ui.ui.errors.StubErrorView
import com.example.di.BaseUIScope
import com.example.splash.R
import com.example.splash.di.SplashModule
import com.example.splash.di.SplashScope
import com.example.splash.di.SplashScopeFactory
import kotlinx.android.synthetic.main.activity_splash.*
import toothpick.Toothpick

class SplashActivity : AppCompatActivity(), StubErrorView, LoadingView {

    override fun showStub() {
        network.visibility = View.VISIBLE
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun hideStub() {
        network.visibility = View.GONE
    }

    private val factory = Toothpick.openScopes(BaseUIScope.NAME, SplashScope.SPLASH_SCOPE)
            .apply {
                installModules(SplashModule())
            }
            .getInstance(SplashScopeFactory::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val splashViewModel = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)
        repeat.setOnClickListener {
            splashViewModel.loadConfig()
        }
        ErrorHandler.handleError(splashViewModel, this)
        splashViewModel.loading.observe(this, LoadingObserver(this))
        splashViewModel.imageConfig.observe(this, Observer {
            splashViewModel.nextScreen(this@SplashActivity)
            finish()
        })

        if (savedInstanceState == null) {
            splashViewModel.loadConfig()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Toothpick.closeScope(SplashScope.SPLASH_SCOPE)
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
    }

    override fun showLoading() {
        network.visibility = View.GONE
        progress.visibility = View.VISIBLE
    }
}