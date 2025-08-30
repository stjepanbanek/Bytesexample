package banek.stef.domain.cookies.service

import banek.stef.domain.cookies.api.model.CookieDto
import banek.stef.domain.cookies.api.model.FavoriteCookieRequest
import banek.stef.domain.cookies.service.model.Cookie
import banek.stef.domain.cookies.service.model.CookieStatus
import banek.stef.domain.cookies.service.model.CookieStatusUpdateParams

internal fun CookieDto.toCookie(): Cookie {
    return Cookie(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl,
        status = CookieStatus.NOT_FAVORITE,
    )
}

internal fun CookieStatusUpdateParams.toRequest(): FavoriteCookieRequest {
    return FavoriteCookieRequest(
        cookieId = this.cookieId,
        isFavorite = when (this.newStatus) {
            CookieStatus.FAVORITE -> true
            CookieStatus.NOT_FAVORITE -> false
            CookieStatus.LOADING -> throw IllegalArgumentException("Cannot map LOADING status to API request")
        }
    )
}