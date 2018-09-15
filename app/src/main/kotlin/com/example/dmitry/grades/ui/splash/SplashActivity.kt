package com.example.dmitry.grades.ui.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.dmitry.grades.R
import com.example.dmitry.grades.di.Scopes
import com.example.dmitry.grades.ui.RemoteScopeFactory
import com.example.dmitry.grades.ui.base.observers.ErrorObserver
import com.example.dmitry.grades.ui.base.observers.LoadingObserver
import com.example.dmitry.grades.ui.base.ui.activity.BaseActivity
import com.example.dmitry.grades.ui.base.ui.errors.LoadingView
import com.example.dmitry.grades.ui.base.ui.errors.StubErrorView
import com.example.dmitry.grades.ui.movie.MainActivity
import toothpick.Toothpick

class SplashActivity : BaseActivity(), StubErrorView, LoadingView {

    private var progress: View? = null

    private var network: View? = null

    override fun showStub() {
        network?.visibility = View.VISIBLE
    }

    private lateinit var splashViewModel: SplashViewModel

    val factory = Toothpick.openScope(Scopes.REMOTE_SCOPE).getInstance(RemoteScopeFactory::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashViewModel = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)
        progress = findViewById(R.id.progress)
        network = findViewById(R.id.network)
        findViewById<Button>(R.id.repeat).setOnClickListener {
            splashViewModel.loadConfig()
        }
        splashViewModel.error.observe(this, ErrorObserver(stubErrorView = this))
        splashViewModel.loading.observe(this, LoadingObserver(this))
        splashViewModel.imageConfig.observe(this, Observer {
            startActivity(MainActivity.makeIntent(this@SplashActivity))
            finish()
        })

        if (savedInstanceState == null) {
            splashViewModel.loadConfig()
        }
    }

    override fun hideLoading(state: Boolean) {
        progress?.visibility = View.GONE
    }

    override fun showLoading(state: Boolean) {
        network?.visibility = View.GONE
        progress?.visibility = View.VISIBLE
    }
}