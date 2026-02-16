package com.example.taskweave.home.presentation.taskScreen

import com.example.taskweave.home.domain.model.Project
import com.example.taskweave.home.domain.model.Task

sealed interface TaskUiState {
    object Loading : TaskUiState

    data class Success(val tasks: List<Task>, val project: Project, val isAddingTask: Boolean) :
        TaskUiState

    data class Error(val message: String) : TaskUiState
}
