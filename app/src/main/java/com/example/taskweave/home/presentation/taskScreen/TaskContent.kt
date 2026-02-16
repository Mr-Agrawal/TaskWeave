package com.example.taskweave.home.presentation.taskScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskweave.home.presentation.common.ErrorScreen
import com.example.taskweave.home.presentation.common.LoadingScreen

@Composable
fun TaskContent(
    uiState: TaskUiState,
    onToggle: (String) -> Unit,
    onDelete: (String) -> Unit,
    onRename: (String, String) -> Unit,
    onCommitNewTask: (String) -> Unit,
    onCancelNewTask: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is TaskUiState.Error -> ErrorScreen(uiState.message, Modifier.fillMaxSize())
            TaskUiState.Loading -> LoadingScreen(modifier.fillMaxSize())
            is TaskUiState.Success -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    ElevatedCard(
                        shape = MaterialTheme.shapes.large,
                        elevation = CardDefaults.elevatedCardElevation(2.dp),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        TaskProjectHeader(
                            project = uiState.project, modifier = Modifier.padding(16.dp)
                        )
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.large,
                        elevation = CardDefaults.elevatedCardElevation(8.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                        ),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        LazyColumn(
                            contentPadding = PaddingValues(
                                horizontal = 16.dp, vertical = 12.dp
                            ),
                        ) {
                            if (uiState.isAddingTask) {
                                item {
                                    EditableNewTaskRow(
                                        onCommit = onCommitNewTask,
                                        onCancel = onCancelNewTask,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                }
                            }
                            items(uiState.tasks, key = { it.id }) { task ->
                                TaskRow(
                                    task = task,
                                    onToggle = { onToggle(task.id) },
                                    onDelete = { onDelete(task.id) },
                                    onRename = { onRename(task.id, it) },
                                    modifier = Modifier.animateItem()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}