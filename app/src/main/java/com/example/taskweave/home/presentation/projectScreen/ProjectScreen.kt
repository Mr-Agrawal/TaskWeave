package com.example.taskweave.home.presentation.projectScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskweave.home.presentation.projectScreen.components.ProjectBottomSheet
import com.example.taskweave.home.presentation.projectScreen.components.ProjectContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectScreen(
    projectViewModel: ProjectViewModel,
    onNavigateToTasks: (String) -> Unit,
    onNavigateToCreateProject: () -> Unit,
    onNavigateToJoinProject: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: ProjectUiState by projectViewModel.uiState.collectAsStateWithLifecycle()
    var showProjectActionSheet by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            ProjectTopAppBar()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showProjectActionSheet = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp
                )
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Project")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->
        ProjectContent(
            uiState = uiState,
            onNavigateToTasks = onNavigateToTasks,
            onDelete = projectViewModel::delete,
            modifier = modifier.padding(paddingValues),

            )
    }
    if (showProjectActionSheet) {
        ProjectBottomSheet(
            onAddProject = onNavigateToCreateProject,
            onJoinProject = onNavigateToJoinProject,
            onDismiss = { showProjectActionSheet = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Projects",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        ),
        modifier = modifier
    )
}