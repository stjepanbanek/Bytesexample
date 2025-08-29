package banek.stef.domain.cookies.service

import banek.stef.domain.cookies.api.model.CookieDto
import banek.stef.domain.cookies.service.model.Cookie

internal fun CookieDto.toCookie(): Cookie {
    return Cookie(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl
    )
}