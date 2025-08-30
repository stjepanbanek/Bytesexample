package banek.stef.core.navigation

import androidx.navigation3.runtime.NavBackStack
import kotlinx.coroutines.flow.Flow

typealias NavAction = NavBackStack.() -> Unit

interface Navigator {

    val navFlow: Flow<NavAction>

    fun navigateTo(route: Route)

    fun navigateBack()

    fun navigateBackTo(route: Route)
}