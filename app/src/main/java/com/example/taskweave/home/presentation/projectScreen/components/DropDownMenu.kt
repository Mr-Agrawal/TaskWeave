package com.example.taskweave.home.presentation.projectScreen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProjectCardDropDownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onShare: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        expanded = expanded, onDismissRequest = onDismissRequest, modifier = modifier
    ) {
        DropdownMenuItem(
            text = { Text("Share", style = MaterialTheme.typography.bodyLarge) },
            onClick = {
                onDismissRequest()
                onShare()
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            })
        DropdownMenuItem(text = {
            Text(
                text = "Delete",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        }, onClick = {
            onDismissRequest()
            onDelete()
        }, leadingIcon = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        })
    }
}