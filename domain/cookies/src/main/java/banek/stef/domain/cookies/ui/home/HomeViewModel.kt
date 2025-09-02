package banek.stef.domain.cookies.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import banek.stef.api.authentication.AuthenticationProvider
import banek.stef.core.navigation.Navigator
import banek.stef.domain.cookies.service.CookiesService
import banek.stef.domain.cookies.service.model.CookieStatusUpdateParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val cookieService: CookiesService,
    private val navigator: Navigator,
    authenticationProvider: AuthenticationProvider,
) : ViewModel() {

    private val isLoading = MutableStateFlow(true)

    val viewState = combine(
        cookieService.cookiesFlow(),
        authenticationProvider.currentUser.filterNotNull().map { it.name },
        isLoading,
    ) { cookiesResult, userName, isLoading ->
        when {
            isLoading -> HomeState.Loading
            cookiesResult.isSuccess -> HomeState.Content(
                userName = userName,
                cookies = cookiesResult.getOrThrow()
            )
            else -> HomeState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = HomeState.Loading,
    )

    private fun refreshCookies() {
        viewModelScope.launch {
            isLoading.update { true }
            cookieService.refreshCookies()
            isLoading.update { false }
        }
    }

    fun onInteraction(interaction: HomeInteraction) {
        when (interaction) {
            HomeInteraction.LogoutClicked -> {
                navigator.navigateBack()
            }

            HomeInteraction.RetryClicked -> {
                refreshCookies()
            }

            is HomeInteraction.CookieClicked -> {
                // Navigate to details
            }
        }
    }
}

