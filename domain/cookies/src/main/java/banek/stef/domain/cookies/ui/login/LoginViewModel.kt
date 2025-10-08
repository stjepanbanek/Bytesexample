package banek.stef.domain.cookies.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import banek.stef.api.authentication.AuthenticationProvider
import banek.stef.api.authentication.AuthenticationParams
import banek.stef.core.navigation.Navigator
import banek.stef.core.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class LoginViewModel(
    private val authenticationProvider: AuthenticationProvider,
    private val navigator: Navigator,
) : ViewModel() {

    var usernameInputState by mutableStateOf(TextFieldValue(""))
        private set

    var passwordInputState by mutableStateOf(TextFieldValue(""))
        private set

    private val errorPresentFlow = MutableStateFlow(false)

    val viewState = combine(
        snapshotFlow { usernameInputState.text.isNotEmpty() }.distinctUntilChanged(),
        snapshotFlow { passwordInputState.text.isNotEmpty() }.distinctUntilChanged(),
        errorPresentFlow,
    ) { isUsernameFilled, isPasswordFilled, isErrorPresent ->
        LoginState(
            loginButtonEnabled = isUsernameFilled && isPasswordFilled,
            errorPresent = isErrorPresent,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(500),
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
            authenticationProvider.login(
                AuthenticationParams(
                    username = usernameInputState.text,
                    password = passwordInputState.text,
                )
            )
                .onSuccess {
                    navigator.navigateTo(Route.Home)
                }
                .onFailure {
                    errorPresentFlow.value = true
                }
        }
    }
}