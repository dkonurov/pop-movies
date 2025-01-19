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
import androidx.lifecycle.lifecycleScope
import com.example.base.extensions.viewModel
import com.example.core.model.PresentationError
import com.example.core.ui.Theming
import com.example.splash.di.DaggerSplashComponent
import com.example.splash.di.SplashDependenciesImpl
import com.example.splash.domain.SplashNavigator
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.produceIn

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component = DaggerSplashComponent.factory().create(SplashDependenciesImpl())
        val splashViewModel = viewModel { component.getSplashViewModel().get() }
        setContent { SplashScreen(splashViewModel) }
        if (savedInstanceState == null) splashViewModel.loadConfig()
        observeSideEffects(component.getSplashNavigator(), splashViewModel)
    }

    private fun observeSideEffects(
        navigator: SplashNavigator,
        splashViewModel: SplashViewModel
    ) {
        splashViewModel.sideEffect
            .onEach {
                when (it) {
                    is SideEffects.NextScreen -> navigator.nextScreen(this)
                }
            }
            .produceIn(lifecycleScope)

    }

    @Composable
    private fun SplashScreen(viewModel: SplashViewModel) = MaterialTheme {
        SplashBackground()
        when (val state = viewModel.state) {
            is State.Loading -> Progress()
            is State.Error -> ErrorScreen(state.present, viewModel)
        }

    }

    @Composable
    @Preview
    private fun Progress() {
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
    private fun ErrorScreen(
        error: PresentationError,
        viewModel: SplashViewModel
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Theming.colorPrimaryDark)
        ) {
            Text(text = error.message, color = Color.White)
            Button(onClick = { viewModel.loadConfig() }, Modifier.padding(0.dp, 8.dp)) {
                Text(text = "Repeat")
            }
        }
    }

    @Composable
    @Preview
    private fun SplashBackground() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Splash", fontSize = 50.sp, color = Theming.colorAccent)
        }
    }
}