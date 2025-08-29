package banek.stef.domain.cookies.ui.login

import androidx.compose.ui.text.input.TextFieldValue

internal sealed interface LoginInteraction {
    data class UsernameChanged(val newValue: TextFieldValue) : LoginInteraction
    data class PasswordChanged(val newValue: TextFieldValue) : LoginInteraction
    data object LoginClicked : LoginInteraction
}