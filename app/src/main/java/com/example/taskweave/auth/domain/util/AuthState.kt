package com.example.taskweave.auth.domain.util

import com.example.taskweave.auth.domain.model.User

sealed class AuthState {
    object Loading : AuthState()
    object Unauthenticated : AuthState()
    data class Authenticated(val user: User) : AuthState()
}