package banek.stef.domain.cookies.service

import banek.stef.domain.cookies.api.CookieApi
import banek.stef.domain.cookies.api.model.FavoriteCookieRequest
import banek.stef.domain.cookies.service.model.Cookie
import banek.stef.domain.cookies.service.model.CookieStatus
import banek.stef.domain.cookies.service.model.CookieStatusUpdateParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

internal class CookieServiceImpl(
    private val cookieApi: CookieApi,
) : CookiesService {

    private val serviceScope = CoroutineScope(Dispatchers.Default) + SupervisorJob()

    private val cookiesStateFlow = MutableStateFlow<List<Cookie>?>(null)

    override suspend fun refreshCookies(): Result<Unit> {
        return cookieApi.fetchCookies()
            .onSuccess { response ->
                cookiesStateFlow.value = response.map { it.toCookie() }
            }
            .map {}
    }

    override fun cookiesFlow(): Flow<List<Cookie>> = cookiesStateFlow.filterNotNull()

    override fun favoriteCookie(params: CookieStatusUpdateParams) {
        serviceScope.launch {
            // Get current status
            val currentStatus = cookiesStateFlow.value?.find { it.id == params.cookieId }?.status
                ?: return@launch
            // Show loading
            updateCookieStatus(params.cookieId, CookieStatus.LOADING)
            // Try updating the status
            cookieApi.favoriteCookie(params.toRequest())
                .onSuccess {
                    updateCookieStatus(params.cookieId, params.newStatus)
                }
                .onFailure {
                    // Revert to previous status on failure
                    updateCookieStatus(params.cookieId, currentStatus)
                    // Handle error by showing message or something
                    // ...
                }
        }
    }

    private fun updateCookieStatus(cookieId: String, status: CookieStatus) {
        val currentCookies = cookiesStateFlow.value ?: return
        val updatedCookies = currentCookies.map { cookie ->
            if (cookie.id == cookieId) {
                cookie.copy(status = status)
            } else {
                cookie
            }
        }
        cookiesStateFlow.value = updatedCookies
    }
}