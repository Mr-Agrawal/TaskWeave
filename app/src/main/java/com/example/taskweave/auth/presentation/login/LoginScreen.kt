package com.example.taskweave.auth.presentation.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.taskweave.R

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel, onNavigateToSignUp: () -> Unit, modifier: Modifier = Modifier
) {
    val uiState = loginViewModel.uiState.collectAsState()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background, modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            LoginContent(
                uiState = uiState.value,
                onEmailChange = loginViewModel::onEmailChange,
                onPasswordChange = loginViewModel::onPasswordChange,
                onNavigateToSignUp = onNavigateToSignUp,
                login = loginViewModel::login,
                modifier = Modifier.align(Alignment.Center)
            )
            if (uiState.value.isLoading) {
                TODO()
            }
        }
    }
}

@Composable
fun LoginContent(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    login: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isLoginEnabled = uiState.email.isNotBlank() && uiState.password.isNotBlank()

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.task_logo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp)

        )
        Text(
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = stringResource(R.string.login_to_continue),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            label = { Text(stringResource(R.string.email)) },
            singleLine = true,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = uiState.password,
            onValueChange = { onPasswordChange(it) },
            label = { Text(stringResource(R.string.password)) },
            enabled = !uiState.isLoading,
            visualTransformation = PasswordVisualTransformation(),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions(onDone = { login() }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = login,
            enabled = !uiState.isLoading && isLoginEnabled,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
        ) {
            Text(
                text = stringResource(R.string.login), style = MaterialTheme.typography.titleMedium
            )
        }
        TextButton(
            onClick = {}, enabled = !uiState.isLoading
        ) {
            Text(
                text = stringResource(R.string.forgot_password)
            )
        }
        AnimatedVisibility(
            visible = uiState.error != null,
        ) {
            Text(
                text = uiState.error.orEmpty(),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        TextButton(
            onClick = onNavigateToSignUp,
        ) {
            Text(
                text = stringResource(R.string.do_not_have_account),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
