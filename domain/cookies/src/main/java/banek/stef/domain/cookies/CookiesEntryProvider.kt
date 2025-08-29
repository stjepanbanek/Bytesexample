package banek.stef.domain.cookies

import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import banek.stef.domain.cookies.ui.LoginScreen
import kotlinx.serialization.Serializable

sealed interface CookiesRoute : NavKey {
    @Serializable
    data object Login : CookiesRoute
}

val cookiesEntryProvider = entryProvider<NavKey> {
    entry<CookiesRoute.Login> { LoginScreen() }
}