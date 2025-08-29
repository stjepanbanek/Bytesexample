package banek.stef.domain.cookies.ui

internal data class LoginState(
    val loginButtonEnabled: Boolean = false,
    val errorPresent: Boolean = false,
)