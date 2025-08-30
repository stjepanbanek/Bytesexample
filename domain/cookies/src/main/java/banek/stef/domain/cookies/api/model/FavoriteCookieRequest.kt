package banek.stef.domain.cookies.api.model

import kotlinx.serialization.Serializable

@Serializable
internal data class FavoriteCookieRequest(
    val cookieId: String,
    val isFavorite: Boolean,
)