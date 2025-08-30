package banek.stef.domain.cookies.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.withSaveLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import banek.stef.core.theme.BytesExampleTheme
import banek.stef.domain.cookies.service.model.Cookie
import banek.stef.domain.cookies.service.model.CookieStatus
import coil3.compose.AsyncImage
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "An error occurred. Please try again.",
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = onRetryClicked
                ) {
                    Text("Retry")
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.HomeContent(
    viewState: HomeState.Content,
    onInteraction: (HomeInteraction) -> Unit
) {
    Toolbar(
        userName = viewState.userName,
        onLogoutClicked = { onInteraction(HomeInteraction.LogoutClicked) }
    )

    LazyColumn(
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(viewState.cookies) { cookie ->
            CookieItem(
                cookie = cookie,
                onFavoriteToggle = {
                    onInteraction(
                        HomeInteraction.FavoriteClicked(
                            cookie
                        )
                    )
                },
                onCookieClicked = { onInteraction(HomeInteraction.CookieClicked(cookie.id)) }
            )
        }
    }
}

@Composable
private fun CookieItem(
    cookie: Cookie,
    onFavoriteToggle: () -> Unit,
    onCookieClicked: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onCookieClicked() })
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box {
                AsyncImage(
                    model = cookie.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )

                FavoriteButton(
                    status = cookie.status,
                    onFavoriteToggle = onFavoriteToggle,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                )
            }

            Text(
                text = cookie.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
                    .padding(horizontal = 16.dp)
            )

            Text(
                text = cookie.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun FavoriteButton(
    status: CookieStatus,
    onFavoriteToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (status == CookieStatus.LOADING) {
        IconToggleButton(
            checked = false,
            onCheckedChange =  {},
            modifier = modifier
        ) {
            CircularProgressIndicator(
                modifier = modifier
                    .width(36.dp)
                    .height(36.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
        return
    }
    IconToggleButton(
        checked = status == CookieStatus.FAVORITE,
        onCheckedChange = { onFavoriteToggle() },
        modifier = modifier
    ) {
        Icon(
            imageVector = if (status == CookieStatus.FAVORITE) {
                Icons.Filled.Star
            } else {
                Icons.Default.StarOutline
            },
            tint = Color.Yellow,
            contentDescription = null,
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
private fun Toolbar(
    userName: String,
    onLogoutClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .drawWithContent {
                drawIntoCanvas { canvas ->
                    canvas.withSaveLayer(
                        bounds = size.toRect(),
                        paint = Paint(),
                    ) {
                        drawContent()
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.2f)),
                                startY = size.height - 3.dp.toPx(),
                                endY = size.height,
                            ),
                        )
                    }
                }
            }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            style = MaterialTheme.typography.headlineSmall,
            text = "Welcome, $userName",
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = onLogoutClicked
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.Logout,
                contentDescription = "Logout"
            )
        }
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    BytesExampleTheme {
        Column {
            FavoriteButton(status = CookieStatus.FAVORITE, onFavoriteToggle = {})
            FavoriteButton(status = CookieStatus.NOT_FAVORITE, onFavoriteToggle = {})
            FavoriteButton(status = CookieStatus.LOADING, onFavoriteToggle = {})
        }
    }

}