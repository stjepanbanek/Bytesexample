package banek.stef.domain.cookies.service.model

internal data class CookieStatusUpdateParams(
    val cookieId: String,
    val newStatus: CookieStatus,
)