package com.example.taskweave.home.domain.repository

import com.example.taskweave.home.domain.model.Project
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun observeProjects(): Flow<List<Project>>
    fun observeProject(projectId: String): Flow<Project>
//    fun observeTasks(projectId: String): Flow<List<Task>>

    suspend fun createProject(name: String)
    suspend fun deleteProject(projectId: String)
    suspend fun joinProject(projectId: String)


//    suspend fun createTask(projectId: String, name: String)
//    suspend fun deleteTask(projectId: String, taskId: String)
//    suspend fun renameTask(projectId: String, taskId: String, newName: String)
//    suspend fun toggleTask(projectId: String, taskId: String)


}