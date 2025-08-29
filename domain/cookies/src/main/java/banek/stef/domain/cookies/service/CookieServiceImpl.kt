package banek.stef.domain.cookies.service

import banek.stef.domain.cookies.api.CookieApi
import banek.stef.domain.cookies.service.model.Cookie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

internal class CookieServiceImpl(
    private val cookieApi: CookieApi,
) : CookiesService {

    private val cookiesStateFlow = MutableStateFlow<List<Cookie>?>(null)

    override suspend fun refreshCookies(): Result<Unit> {
        return cookieApi.fetchCookies()
            .onSuccess { response ->
                cookiesStateFlow.value = response.map { it.toCookie() }
            }
            .map {}
    }

    override fun cookiesFlow(): Flow<List<Cookie>> = cookiesStateFlow.filterNotNull()
}