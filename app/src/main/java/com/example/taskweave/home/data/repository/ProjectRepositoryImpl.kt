package com.example.taskweave.home.data.repository

import com.example.taskweave.home.data.remote.dto.ProjectDto
import com.example.taskweave.home.data.remote.dto.TaskDto
import com.example.taskweave.home.data.remote.dto.toProject
import com.example.taskweave.home.data.remote.dto.toTask
import com.example.taskweave.home.domain.model.Project
import com.example.taskweave.home.domain.model.Task
import com.example.taskweave.home.domain.repository.ProjectRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ProjectRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth, private val firebaseFirestore: FirebaseFirestore
) : ProjectRepository {

    private val projectCollection get() = firebaseFirestore.collection("projects")

    override fun observeProjects(): Flow<List<Project>> = callbackFlow {
        val userId = firebaseAuth.currentUser?.uid
        if (userId == null) {
            close(IllegalStateException("Not logged in"))
            return@callbackFlow
        }
        val listener = projectCollection.whereArrayContains("memberIds", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                val projects = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(ProjectDto::class.java)?.copy(id = doc.id)?.toProject(userId)
                } ?: emptyList()
                trySend(projects)
            }
        awaitClose { listener.remove() }
    }

    override fun observeProject(projectId: String): Flow<Project> = callbackFlow {
        val userId = firebaseAuth.currentUser?.uid
        if (userId == null) {
            close(IllegalStateException("Not logged in"))
            return@callbackFlow
        }

        val listener =
            projectCollection.document(projectId).addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null || !snapshot.exists()) return@addSnapshotListener
                val project = snapshot.toObject(ProjectDto::class.java)?.copy(id = snapshot.id)
                    ?.toProject(userId) ?: Project()
                trySend(project)
            }
        awaitClose { listener.remove() }
    }

    override fun observeTasks(projectId: String): Flow<List<Task>> = callbackFlow {
        val listener = projectCollection.document(projectId).collection("tasks")
            .orderBy("completed", Query.Direction.ASCENDING)
            .orderBy("createdAt", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                val tasks = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(TaskDto::class.java)?.copy(id = doc.id)?.toTask()
                } ?: emptyList()
                trySend(tasks)
            }
        awaitClose { listener.remove() }
    }


    override suspend fun createProject(name: String) {
        val userId = firebaseAuth.currentUser?.uid ?: return
        val projectRef = projectCollection.document()
        val dto = ProjectDto(
            id = projectRef.id,
            name = name,
            ownerId = userId,
            memberIds = listOf(userId),
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        projectRef.set(dto)
    }

    override suspend fun deleteProject(projectId: String) {
        val currentUserId = firebaseAuth.currentUser?.uid ?: return
        val projectRef = projectCollection.document(projectId)
        val snapshot = projectRef.get().await()
        val ownerId = snapshot.getString("ownerId")
        if (ownerId != currentUserId) {
            throw IllegalStateException("Only owner can delete project")
        }
        projectRef.delete().await()
    }

    override suspend fun joinProject(projectId: String) {
        val userId = firebaseAuth.currentUser?.uid ?: return
        projectCollection.document(projectId).update("memberIds", FieldValue.arrayUnion(userId))
    }


    override suspend fun createTask(projectId: String, name: String) {
        val projectRef = projectCollection.document(projectId)
        val taskRef = projectRef.collection("tasks").document()

        val taskDto = TaskDto(
            id = taskRef.id,
            projectId = projectId,
            name = name,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )

        val batch = firebaseFirestore.batch()
        batch.set(taskRef, taskDto)
        batch.update(projectRef, "totalTaskCount", FieldValue.increment(1))
        batch.commit()
    }

    override suspend fun deleteTask(projectId: String, taskId: String) {
        val projectRef = projectCollection.document(projectId)
        val taskRef = projectRef.collection("tasks").document(taskId)
        val isComplete = taskRef.get().await().getBoolean("completed") ?: false

        val batch = firebaseFirestore.batch()
        batch.delete(taskRef)
        batch.update(projectRef, "totalTaskCount", FieldValue.increment(-1))
        if (isComplete) {
            batch.update(projectRef, "completedTaskCount", FieldValue.increment(-1))
        }
        batch.commit()
    }

    override suspend fun renameTask(projectId: String, taskId: String, newName: String) {
        projectCollection.document(projectId).collection("tasks").document(taskId)
            .update(mapOf("name" to newName, "updatedAt" to System.currentTimeMillis()))
    }

    override suspend fun toggleTask(projectId: String, taskId: String) {
        val projectRef = projectCollection.document(projectId)
        val taskRef = projectRef.collection("tasks").document(taskId)

        val isCurrentlyCompleted = taskRef.get().await().getBoolean("completed") ?: false
        val newStatus = !isCurrentlyCompleted

        val batch = firebaseFirestore.batch()
        batch.update(
            taskRef, mapOf("completed" to newStatus, "updatedAt" to System.currentTimeMillis())
        )

        val incrementValue = if (newStatus) 1 else -1L
        batch.update(projectRef, "completedTaskCount", FieldValue.increment(incrementValue))
        batch.commit()
    }
}