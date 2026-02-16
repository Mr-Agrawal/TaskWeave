package com.example.taskweave.auth

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskweave.auth.presentation.login.LoginScreen
import com.example.tryfbfs.auth.presentation.signup.SignUpScreen

@Composable
fun AuthNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = "LoginScreen"
    ) {

        composable("LoginScreen") {
            LoginScreen(
                loginViewModel = hiltViewModel(),
                onNavigateToSignUp = { navController.navigate("SignUpScreen") }
            )
        }
        composable("SignUpScreen") {
            SignUpScreen(
                signUpViewModel = hiltViewModel(),
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
    }
}