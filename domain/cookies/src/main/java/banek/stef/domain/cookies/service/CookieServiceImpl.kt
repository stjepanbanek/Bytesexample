package banek.stef.domain.cookies.service

import banek.stef.domain.cookies.api.CookieApi
import banek.stef.domain.cookies.service.model.Cookie
import banek.stef.domain.cookies.service.model.CookieStatus
import banek.stef.domain.cookies.service.model.CookieStatusUpdateParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

internal class CookieServiceImpl(
    private val cookieApi: CookieApi,
) : CookiesService {

    private val serviceScope = CoroutineScope(Dispatchers.Default) + SupervisorJob()

    private val cookiesResultSharedFlow = MutableSharedFlow<Result<List<Cookie>>>()

    override fun refreshCookies() {
        serviceScope.launch {
            val result = cookieApi.fetchCookies().map { list -> list.map { it.toCookie() } }
            cookiesResultSharedFlow.emit(result)
        }
    }

    override fun cookiesFlow(): Flow<Result<List<Cookie>>> = cookiesResultSharedFlow
        .onSubscription { refreshCookies() }
        .shareIn(
            scope = serviceScope,
            started = SharingStarted.Lazily,
            replay = 1,
        )
}