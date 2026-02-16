package com.example.taskweave.home.presentation.projectScreen.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.taskweave.R

@Composable
fun DeleteConfirmationDialog(
    onDismiss: () -> Unit, onConfirm: () -> Unit, message: String
) {
    AlertDialog(confirmButton = {
        TextButton(onClick = {
            onConfirm()
            onDismiss()
        }) {
            Text(
                text = stringResource(R.string.confirm), color = MaterialTheme.colorScheme.error
            )
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(text = stringResource(R.string.cancel))
        }
    }, onDismissRequest = onDismiss, title = {
        Text(
            text = stringResource(R.string.delete_confirmation_title, message),
            style = MaterialTheme.typography.titleLarge
        )
    }, text = {
        Text(
            text = stringResource(R.string.delete_confirmation_message),
            maxLines = 2,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    )
}