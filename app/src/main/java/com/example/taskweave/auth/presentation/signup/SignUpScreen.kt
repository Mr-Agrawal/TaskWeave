package com.example.tryfbfs.auth.presentation.signup

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
import com.example.taskweave.auth.presentation.common.LoadingOverlay
import com.example.taskweave.auth.presentation.signup.SignUpUiState
import com.example.taskweave.auth.presentation.signup.SignUpViewModel


@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel, onNavigateToLogin: () -> Unit, modifier: Modifier = Modifier
) {
    val uiState = signUpViewModel.uiState.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            SignUpContent(
                uiState = uiState.value,
                onEmailChange = { signUpViewModel.onEmailChange(it) },
                onPasswordChange = { signUpViewModel.onPasswordChange(it) },
                onNavigateToLogin = onNavigateToLogin,
                signUp = { signUpViewModel.signUp() },
                modifier = Modifier.align(Alignment.Center)
            )
            if (uiState.value.isLoading) {
                LoadingOverlay()
            }
        }
    }
}

@Composable
fun SignUpContent(
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNavigateToLogin: () -> Unit,
    signUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isSignUpEnabled = uiState.email.isNotBlank() && uiState.password.isNotBlank()
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
            text = stringResource(R.string.create_account),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = stringResource(R.string.signup_to_get_started),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = uiState.email,
            onValueChange = { onEmailChange(it) },
            enabled = !uiState.isLoading,
            label = { Text(stringResource(R.string.email)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            )
        )
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { onPasswordChange(it) },
            enabled = !uiState.isLoading,
            label = { Text(stringResource(R.string.enter_password)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardActions = KeyboardActions(onDone = { signUp() }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { signUp() },
            enabled = !uiState.isLoading && isSignUpEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = stringResource(R.string.signup),
                style = MaterialTheme.typography.labelLarge
            )
        }

        AnimatedVisibility(
            visible = uiState.error != null
        ) {
            Text(
                text = uiState.error.orEmpty(),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        TextButton(
            onClick = { onNavigateToLogin() }) {
            Text(
                text = stringResource(R.string.already_have_account),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
