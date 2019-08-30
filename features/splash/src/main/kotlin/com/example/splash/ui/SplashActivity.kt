package com.example.splash.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.base.ui.observers.LoadingObserver
import com.example.base.ui.ui.activity.DIActivity
import com.example.base.ui.ui.errors.ErrorHandler
import com.example.base.ui.ui.errors.LoadingView
import com.example.base.ui.ui.errors.StubErrorView
import com.example.di.BaseUIScope
import com.example.splash.R
import com.example.splash.di.SplashModule
import com.example.splash.di.SplashScope
import kotlinx.android.synthetic.main.activity_splash.*
import toothpick.Toothpick

class SplashActivity : DIActivity(), StubErrorView, LoadingView {

    override fun showStub() {
        network.visibility = View.VISIBLE
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun hideStub() {
        network.visibility = View.GONE
    }

    override fun createScope() {
        BaseUIScope.getBaseUIScope().openSubScope(SplashScope.NAME)
                .installModules(SplashModule())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val splashViewModel = Toothpick.openScope(SplashScope.NAME)
                .getInstance(SplashViewModel::class.java)
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

    override fun closeScope() {
        Toothpick.closeScope(SplashScope.NAME)
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
    }

    override fun showLoading() {
        network.visibility = View.GONE
        progress.visibility = View.VISIBLE
    }
}