package com.example.taskweave

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskweave.auth.AuthNavHost
import com.example.taskweave.auth.AuthViewModel
import com.example.taskweave.auth.domain.util.AuthState
import com.example.taskweave.home.HomeNavHost
import com.example.taskweave.home.presentation.common.LoadingScreen

@Composable
fun AppRoot(modifier: Modifier = Modifier, authViewModel: AuthViewModel = hiltViewModel()) {
    val authState by authViewModel.authState.collectAsStateWithLifecycle()

    Crossfade(
        targetState = authState,
        animationSpec = tween(durationMillis = 400),
        label = "app_root_crossfade",
        modifier = modifier.fillMaxSize()
    ) { state ->
        when (state) {
            is AuthState.Authenticated -> {
                HomeNavHost()
            }

            is AuthState.Unauthenticated -> {
                AuthNavHost()
            }

            is AuthState.Loading -> {
                LoadingScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}