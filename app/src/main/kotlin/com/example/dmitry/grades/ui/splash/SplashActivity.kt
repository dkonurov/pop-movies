package com.example.dmitry.grades.ui.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.dmitry.grades.R
import com.example.dmitry.grades.di.Scopes
import com.example.dmitry.grades.ui.RemoteScopeFactory
import com.example.dmitry.grades.ui.base.observers.LoadingObserver
import com.example.dmitry.grades.ui.base.ui.errors.ErrorHandler
import com.example.dmitry.grades.ui.base.ui.errors.LoadingView
import com.example.dmitry.grades.ui.base.ui.errors.StubErrorView
import com.example.dmitry.grades.ui.movie.MainActivity
import toothpick.Toothpick

class SplashActivity : AppCompatActivity(), StubErrorView, LoadingView {

    private var progress: View? = null

    private var network: View? = null

    override fun showStub() {
        network?.visibility = View.VISIBLE
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun hideStub() {
        network?.visibility = View.GONE
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
        ErrorHandler.handleError(splashViewModel, this)
        splashViewModel.loading.observe(this, LoadingObserver(this))
        splashViewModel.imageConfig.observe(this, Observer {
            startActivity(MainActivity.makeIntent(this@SplashActivity))
            finish()
        })

        if (savedInstanceState == null) {
            splashViewModel.loadConfig()
        }
    }

    override fun hideLoading() {
        progress?.visibility = View.GONE
    }

    override fun showLoading() {
        network?.visibility = View.GONE
        progress?.visibility = View.VISIBLE
    }
}