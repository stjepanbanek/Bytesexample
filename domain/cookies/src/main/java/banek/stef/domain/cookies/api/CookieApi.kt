package banek.stef.domain.cookies.api

import banek.stef.domain.cookies.api.model.CookieDto
import banek.stef.domain.cookies.api.model.FavoriteCookieRequest

internal interface CookieApi {
    suspend fun fetchCookies(): Result<List<CookieDto>>
    suspend fun favoriteCookie(request: FavoriteCookieRequest): Result<Unit>
}