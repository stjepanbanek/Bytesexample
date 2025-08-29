package banek.stef.domain.cookies.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import banek.stef.api.authentication.AuthenticationApi
import banek.stef.api.authentication.AuthenticationParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class LoginViewModel(
    private val authenticationApi: AuthenticationApi,
) : ViewModel() {

    var usernameInputState by mutableStateOf(TextFieldValue(""))
        private set

    var passwordInputState by mutableStateOf(TextFieldValue(""))
        private set

    private var errorPresentFlow = MutableStateFlow(false)

    val viewState = combine(
        snapshotFlow { usernameInputState }.map { it.text.isNotEmpty() },
        snapshotFlow { passwordInputState }.map { it.text.isNotEmpty() },
        errorPresentFlow,
    ) { isUsernameFilled, isPasswordFilled, isErrorPresent ->
        LoginState(
            loginButtonEnabled = isUsernameFilled && isPasswordFilled,
            errorPresent = isErrorPresent,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = LoginState()
    )

    fun onInteraction(interaction: LoginInteraction) {
        when (interaction) {
            is LoginInteraction.UsernameChanged -> {
                usernameInputState = interaction.newValue
            }

            is LoginInteraction.PasswordChanged -> {
                passwordInputState = interaction.newValue
            }

            is LoginInteraction.LoginClicked -> login()
        }
    }

    private fun login() {
        viewModelScope.launch {
            authenticationApi.login(
                AuthenticationParams(
                    username = usernameInputState.text,
                    password = passwordInputState.text,
                )
            )
                .onSuccess {
                    // Navigate to next screen
                }
                .onFailure {
                    errorPresentFlow.value = true
                }
        }
    }
}