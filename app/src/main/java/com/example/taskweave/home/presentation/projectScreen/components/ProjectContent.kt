package com.example.taskweave.home.presentation.projectScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskweave.home.presentation.common.ErrorScreen
import com.example.taskweave.home.presentation.common.LoadingScreen
import com.example.taskweave.home.presentation.projectScreen.ProjectUiState

@Composable
fun ProjectContent(
    uiState: ProjectUiState,
    onDelete: (String) -> Unit,
    onNavigateToTasks: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var projectPendingDelete by rememberSaveable { mutableStateOf<String?>(null) }
    var projectIdToShare by rememberSaveable { mutableStateOf<String?>(null) }
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is ProjectUiState.Error -> ErrorScreen(uiState.message, Modifier.fillMaxSize())
            ProjectUiState.Loading -> LoadingScreen()
            is ProjectUiState.Success -> {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.projects, key = { it.id }) { project ->
                        ProjectCard(
                            project = project,
                            modifier = Modifier.fillMaxWidth(),
                            onShare = { projectIdToShare = project.id },
                            onDelete = { projectPendingDelete = project.id },
                            onNavigateToTasks = onNavigateToTasks
                        )
                    }
                }
                projectPendingDelete?.let { projectId ->
                    DeleteConfirmationDialog(
                        onDismiss = { projectPendingDelete = null },
                        onConfirm = {
                            onDelete(projectId)
                            projectPendingDelete = null
                        },
                        message = "project"
                    )
                }

                projectIdToShare?.let { projectId ->
                    ShareProjectDialog(
                        onDismiss = { projectIdToShare = null },
                        projectId = projectId
                    )

                }
            }
        }
    }
}

