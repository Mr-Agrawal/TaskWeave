package com.example.taskweave.home.presentation.projectScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.taskweave.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectBottomSheet(
    onAddProject: () -> Unit,
    onJoinProject: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.create_or_join_project),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(20.dp))
            ListItem(headlineContent = {
                Text(
                    text = stringResource(R.string.create_new_project), style = MaterialTheme.typography.bodyLarge
                )
            }, supportingContent = {
                Text(
                    text = stringResource(R.string.start_fresh_workspace),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }, leadingContent = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }, modifier = Modifier.clickable {
                onAddProject()
                onDismiss()
            })
            Spacer(modifier = Modifier.height(8.dp))
            ListItem(headlineContent = {
                Text(
                    text = stringResource(R.string.join_existing_project), style = MaterialTheme.typography.bodyLarge
                )
            }, supportingContent = {
                Text(
                    text = stringResource(R.string.enter_project_id),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }, leadingContent = {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }, modifier = Modifier.clickable {
                onJoinProject()
                onDismiss()
            })
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}