package banek.stef.domain.cookies.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import banek.stef.api.authentication.AuthenticationApi
import banek.stef.domain.cookies.service.CookiesService
import banek.stef.domain.cookies.service.model.CookieStatusUpdateParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val cookieService: CookiesService,
    authenticationApi: AuthenticationApi,
) : ViewModel() {

    private val didErrorHappenFlow = MutableStateFlow(false)

    init {
        refreshCookies()
    }

    val viewState = combine(
        cookieService.cookiesFlow().onStart { emit(emptyList()) },
        authenticationApi.currentUser.filterNotNull().map { it.name },
        didErrorHappenFlow
    ) { cookies, userName, didErrorHappen ->
        Log.d("StefDebug", "State changed: $cookies, $userName, $didErrorHappen")
        when {
            didErrorHappen -> HomeState.Error
            cookies.isEmpty() -> HomeState.Loading
            else -> HomeState.Content(
                userName = userName,
                cookies = cookies
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = HomeState.Loading,
    )

    private fun refreshCookies() {
        viewModelScope.launch {
            cookieService.refreshCookies()
                .onFailure {
                    didErrorHappenFlow.value = true
                }
        }
    }

    fun onInteraction(interaction: HomeInteraction) {
        when (interaction) {
            HomeInteraction.LogoutClicked -> {
                // Handle logout
            }

            HomeInteraction.RetryClicked -> {
                didErrorHappenFlow.value = false
                refreshCookies()
            }

            is HomeInteraction.CookieClicked -> {
                // Navigate to details
            }

            is HomeInteraction.FavoriteClicked -> {
                cookieService.favoriteCookie(
                    CookieStatusUpdateParams(
                        cookieId = interaction.cookie.id,
                        newStatus = interaction.cookie.status.toggle()
                    )
                )
            }
        }
    }
}

