package com.example.splash.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.base.extensions.viewModel
import com.example.core.ui.Theming
import com.example.splash.di.DaggerSplashComponent
import com.example.splash.di.SplashDependenciesImpl

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component = DaggerSplashComponent.factory().create(SplashDependenciesImpl())
        val splashViewModel = viewModel { component.getSplashViewModel().get() }
        setContent { splashScreen(splashViewModel) }
        if (savedInstanceState == null) splashViewModel.loadConfig()
    }

    @Composable
    private fun splashScreen(viewModel: SplashViewModel) = MaterialTheme {
        splashBackground()
        when (viewModel.state) {
            is State.Loading -> progress()
            is State.Error -> errorScreen(viewModel)
            is State.Success -> viewModel.nextScreen(this)
        }

    }

    @Composable
    @Preview
    private fun progress() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Theming.colorGrey)
        ) {
            CircularProgressIndicator(color = Theming.colorPrimary)
        }
    }

    @Composable
    private fun errorScreen(viewModel: SplashViewModel) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Theming.colorPrimaryDark)
        ) {
            Text(text = "Sorry. Something was going to wrong", color = Color.White)
            Button(onClick = { viewModel.loadConfig() }, Modifier.padding(0.dp, 8.dp)) {
                Text(text = "Repeat")
            }
        }
    }

    @Composable
    @Preview
    private fun splashBackground() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Splash", fontSize = 50.sp, color = Theming.colorAccent)
        }
    }
}