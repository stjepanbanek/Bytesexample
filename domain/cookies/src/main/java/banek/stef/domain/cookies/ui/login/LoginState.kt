package banek.stef.domain.cookies.ui.login

internal data class LoginState(
    val loginButtonEnabled: Boolean = false,
    val errorPresent: Boolean = false,
)