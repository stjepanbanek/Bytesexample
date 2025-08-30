package banek.stef.domain.cookies.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<HomeViewModel>()
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    HomeScreen(
        viewState = viewState,
        onInteraction = viewModel::onInteraction
    )
}

@Composable
private fun HomeScreen(
    viewState: HomeState,
    onInteraction: (HomeInteraction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        when (viewState) {
            HomeState.Loading -> HomeLoading()
            HomeState.Error -> HomeError(
                onRetryClicked = { onInteraction(HomeInteraction.RetryClicked) }
            )
            is HomeState.Content -> HomeContent(
                viewState = viewState,
                onInteraction = onInteraction
            )
        }
    }
}

@Composable
private fun HomeLoading() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
private fun HomeError(
    onRetryClicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Card {
            Column {
                Text(text = "An error occurred. Please try again.")
                Button(
                    onClick = onRetryClicked
                ) {
                    Text("Retry")
                }
            }
        }
    }
}

private fun HomeContent(
    viewState: HomeState.Content,
    onInteraction: (HomeInteraction) -> Unit
) {

}