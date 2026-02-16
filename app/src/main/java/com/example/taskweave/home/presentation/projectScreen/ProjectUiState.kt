package com.example.taskweave.home.presentation.projectScreen

import com.example.taskweave.home.domain.model.Project

sealed interface ProjectUiState {
    object Loading : ProjectUiState
    data class Success(val projects: List<Project>) : ProjectUiState
    data class Error(val message: String) : ProjectUiState
}
