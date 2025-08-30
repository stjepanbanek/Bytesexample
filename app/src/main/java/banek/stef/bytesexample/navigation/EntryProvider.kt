package banek.stef.bytesexample.navigation

import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import banek.stef.core.navigation.Route
import banek.stef.domain.cookies.ui.home.HomeScreen
import banek.stef.domain.cookies.ui.login.LoginScreen

internal val entryProvider = entryProvider<NavKey> {
    entry<Route.Login> { LoginScreen() }
    entry<Route.Home> { HomeScreen() }
}