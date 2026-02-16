package com.example.taskweave.home.presentation.projectScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskweave.home.domain.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@HiltViewModel
class ProjectViewModel @Inject constructor(private val repository: ProjectRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<ProjectUiState> =
        MutableStateFlow(ProjectUiState.Loading)
    val uiState: StateFlow<ProjectUiState> = _uiState.asStateFlow()

    init {
        loadProjects()
    }

    private fun loadProjects() {
        viewModelScope.launch {
            repository.observeProjects().catch { e ->
                _uiState.value = ProjectUiState.Error(e.message ?: "Something Went Wrong")
            }.collect { projects ->
                _uiState.value = ProjectUiState.Success(projects)
            }
        }
    }

    fun createProject(name: String) {
        viewModelScope.launch {
            try {
                repository.createProject(name)
            } catch (e: Exception) {
                _uiState.value = ProjectUiState.Error(e.message ?: "Something Went Wrong")
            }
        }
    }

    fun joinProject(projectId: String) {
        viewModelScope.launch {
            try {
                repository.joinProject(projectId)
            } catch (e: Exception) {
                _uiState.value =
                    ProjectUiState.Error(e.message ?: "Invalid or expire invite code")
            }
        }
    }

    fun delete(id: String) {
        viewModelScope.launch {
            try {
                repository.deleteProject(id)
            } catch (e: Exception) {
                _uiState.value = ProjectUiState.Error(e.message ?: "Failed to delete project")
            }
        }

    }
}