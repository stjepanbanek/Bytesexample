package banek.stef.domain.cookies.service

import banek.stef.domain.cookies.service.model.Cookie
import banek.stef.domain.cookies.service.model.CookieStatusUpdateParams
import kotlinx.coroutines.flow.Flow

internal interface CookiesService {
    fun refreshCookies()
    fun cookiesFlow(): Flow<Result<List<Cookie>>>
}