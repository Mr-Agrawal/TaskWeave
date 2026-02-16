package com.example.taskweave.home.data.remote.dto

import com.example.taskweave.home.domain.model.Task

data class TaskDto(
    val id: String = "",
    val projectId: String = "",
    val name: String = "",
    val completed: Boolean = false,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)

fun TaskDto.toTask(): Task {
    return Task(
        id = id,
        name = name,
        completed = completed
    )
}