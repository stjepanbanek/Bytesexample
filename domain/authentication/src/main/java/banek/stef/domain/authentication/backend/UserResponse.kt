package banek.stef.domain.authentication.backend

internal data class UserResponse(
    val id: String,
    val name: String,
    val email: String
)