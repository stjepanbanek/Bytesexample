package banek.stef.domain.cookies.api.model

import kotlinx.serialization.Serializable

@Serializable
internal data class CookieDto(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
)