package com.example.taskweave.auth.presentation.signup

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val error: String? = null,
    val isLoading: Boolean = false
)
