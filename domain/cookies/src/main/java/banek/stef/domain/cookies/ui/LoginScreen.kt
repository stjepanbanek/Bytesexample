package banek.stef.domain.cookies.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

// TODO: Add navigation logic and make this internal
@Composable
fun LoginScreen() {
    val viewModel = koinViewModel<LoginViewModel>()
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    LoginScreen(
        state = viewState,
        usernameInput = viewModel.usernameInputState,
        passwordInput = viewModel.passwordInputState,
        onInteraction = viewModel::onInteraction
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    usernameInput: TextFieldValue,
    passwordInput: TextFieldValue,
    onInteraction: (LoginInteraction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        TextField(
            value = usernameInput,
            onValueChange = { onInteraction(LoginInteraction.UsernameChanged(it)) },
            label = { Text(text = "Username") },
            placeholder = { Text(text = "Enter your username") },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = passwordInput,
            onValueChange = { onInteraction(LoginInteraction.PasswordChanged(it)) },
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Enter your password") },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        if (state.errorPresent) {
            Text(
                text = "Login failed. Please check your credentials.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = { onInteraction(LoginInteraction.LoginClicked) },
            enabled = state.loginButtonEnabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
    }
}