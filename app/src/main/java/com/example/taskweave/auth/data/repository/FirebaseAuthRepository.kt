package com.example.taskweave.auth.data.repository


import com.example.taskweave.auth.domain.model.User
import com.example.taskweave.auth.domain.model.toUser
import com.example.taskweave.auth.domain.repository.AuthRepository
import com.example.taskweave.auth.domain.util.AuthResponse
import com.google.firebase.auth.FirebaseAuth
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository @Inject constructor(private val auth: FirebaseAuth) : AuthRepository {
    override val currentUser: Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser?.toUser())
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun login(
        email: String, password: String
    ): AuthResponse<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user?.toUser()?.let {
                AuthResponse.Success(it)
            } ?: AuthResponse.Error("User data is null after login.")
        } catch (e: Exception) {
            AuthResponse.Error(e.localizedMessage ?: "An unexpected error occurred during login.")
        }
    }

    override suspend fun signUp(email: String, password: String): AuthResponse<User> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user?.toUser()?.let {
                AuthResponse.Success(it)
            } ?: AuthResponse.Error("User data is null after signup.")
        } catch (e: Exception) {
            AuthResponse.Error(e.localizedMessage ?: "An unexpected error occurred during signup.")
        }
    }

    override fun logout() {
        auth.signOut()
    }
}