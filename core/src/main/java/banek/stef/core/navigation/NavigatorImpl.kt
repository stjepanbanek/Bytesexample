package banek.stef.core.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

internal class NavigatorImpl : Navigator, CoroutineScope by MainScope() {

    private val navActionChannel = Channel<NavAction>(
        capacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override val navFlow = navActionChannel.receiveAsFlow()

    private fun navAction(action: NavAction) {
        launch {
            navActionChannel.trySend(action)
        }
    }

    override fun navigateTo(route: Route) {
        navAction {
            add(route)
        }
    }

    override fun navigateBack() {
        navAction { removeLastOrNull() }
    }

    override fun navigateBackTo(route: Route) {
        navAction {
            if (contains(route)) {
                while (last() != route) {
                    removeLastOrNull()
                }
            }
        }
    }
}