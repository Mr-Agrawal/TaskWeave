package com.example.taskweave.home.domain.model

data class Project(
    val id: String = "",
    val name: String = "",
    val isOwner: Boolean = false,
    val memberCount: Int = 0,
    val totalTaskCount: Int = 0,
    val completedTaskCount: Int = 0,
)