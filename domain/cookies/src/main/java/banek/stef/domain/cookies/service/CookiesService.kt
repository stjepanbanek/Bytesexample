package banek.stef.domain.cookies.service

import banek.stef.domain.cookies.service.model.Cookie
import kotlinx.coroutines.flow.Flow

internal interface CookiesService {
    suspend fun refreshCookies(): Result<Unit>
    fun cookiesFlow(): Flow<List<Cookie>>
}