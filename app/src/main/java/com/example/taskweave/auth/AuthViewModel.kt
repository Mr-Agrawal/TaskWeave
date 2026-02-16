package com.example.taskweave.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskweave.auth.domain.repository.AuthRepository
import com.example.taskweave.auth.domain.util.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {
    val authState: StateFlow<AuthState> = authRepository.currentUser.map { user ->
        if (user != null) AuthState.Authenticated(user)
        else AuthState.Unauthenticated
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(5000),
        initialValue = AuthState.Unauthenticated
    )
}