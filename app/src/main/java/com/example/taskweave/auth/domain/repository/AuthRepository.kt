package com.example.taskweave.auth.domain.repository

import com.example.taskweave.auth.domain.model.User
import com.example.taskweave.auth.domain.util.AuthResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    suspend fun login(email: String, password: String): AuthResponse<User>
    suspend fun signUp(email: String, password: String): AuthResponse<User>
    fun logout()
}