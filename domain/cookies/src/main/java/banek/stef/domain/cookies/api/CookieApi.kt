package banek.stef.domain.cookies.api

import banek.stef.domain.cookies.api.model.CookieDto

internal interface CookieApi {
    suspend fun fetchCookies(): Result<List<CookieDto>>
}