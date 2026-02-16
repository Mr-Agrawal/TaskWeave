package com.example.taskweave.home.presentation.taskScreen


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskweave.home.domain.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
class TaskViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val projectRepository: ProjectRepository,
) : ViewModel() {
    private val projectId: String = checkNotNull(saveStateHandle["projectId"])

    val _uiState = MutableStateFlow<TaskUiState>(TaskUiState.Loading)
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    private val _isAddingTask = MutableStateFlow(false)

    init {
        observeTasks()
    }

    private fun observeTasks() {
        viewModelScope.launch {
            combine(
                projectRepository.observeProject(projectId),
                projectRepository.observeTasks(projectId),
                _isAddingTask
            ) { project, tasks, isAddingTask ->
                TaskUiState.Success(
                    project = project, tasks = tasks, isAddingTask = isAddingTask
                )
            }.catch { e ->
                TaskUiState.Error(e.message ?: "Failed to load tasks")
            }.collect { tasks ->
                _uiState.value = tasks
            }
        }
    }

    fun startAddingTask() {
        _isAddingTask.value = true
    }

    fun cancelAddingTask() {
        _isAddingTask.value = false
    }

    fun commitNewTask(taskName: String) {
        val trimmedName = taskName.trim()
        if (trimmedName.isBlank()) {
            cancelAddingTask()
            return
        }

        viewModelScope.launch {
            projectRepository.createTask(projectId, trimmedName)
        }
    }


    fun toggleTask(taskId: String) {
        viewModelScope.launch {
            projectRepository.toggleTask(projectId, taskId)
        }

    }

    fun renameTask(taskId: String, newName: String) {
        val trimmedName = newName.trim()
        if (trimmedName.isBlank()) return

        viewModelScope.launch {
            projectRepository.renameTask(projectId, taskId, newName)
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            projectRepository.deleteTask(projectId, taskId)
        }
    }
}