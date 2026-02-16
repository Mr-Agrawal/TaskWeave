package com.example.taskweave.auth.presentation.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val error: String? = null,
    val isLoading: Boolean = false
)
