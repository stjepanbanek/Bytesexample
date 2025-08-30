package banek.stef.bytesexample

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import banek.stef.bytesexample.navigation.ObserveAsEvents
import banek.stef.bytesexample.navigation.entryProvider
import banek.stef.core.navigation.Navigator
import banek.stef.core.navigation.Route
import banek.stef.core.theme.BytesExampleTheme
import org.koin.compose.koinInject

@Composable
fun BytesScaffold() {
    BytesExampleTheme {
        val backStack = rememberNavBackStack<Route>(Route.Login)
        val navigator = koinInject<Navigator>()

        ObserveAsEvents(navigator.navFlow) {
            it(backStack)
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
            NavDisplay(
                backStack = backStack,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        PaddingValues(
                            top = 0.dp,
                            bottom = innerPadding.calculateBottomPadding(),
                            start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                            end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
                        )
                    ),
                entryProvider = entryProvider,
                transitionSpec = {
                    ContentTransform(
                        slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start),
                        slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End),
                    )
                },
                popTransitionSpec = {
                    ContentTransform(
                        slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start),
                        slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End),
                    )
                }
            )
        }
    }
}