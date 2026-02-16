package com.example.taskweave.auth.domain.util

sealed class AuthResponse<out T> {
    data class Success<out T>(val data: T?) : AuthResponse<T>()
    data class Error(val message: String) : AuthResponse<Nothing>()
}