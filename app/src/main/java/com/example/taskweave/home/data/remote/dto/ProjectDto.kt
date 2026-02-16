package com.example.taskweave.home.data.remote.dto

import com.example.taskweave.home.domain.model.Project

data class ProjectDto(
    val id: String = "",
    val name: String = "",
    val ownerId: String = "",
    val memberIds: List<String> = emptyList(),
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    val totalTaskCount: Int = 0,
    val completedTaskCount: Int = 0
)

fun ProjectDto.toProject(currentUserId: String): Project {
    return Project(
        id = id,
        name = name,
        isOwner = ownerId == currentUserId,
        memberCount = memberIds.size,
        completedTaskCount = completedTaskCount,
        totalTaskCount = totalTaskCount,
    )
}