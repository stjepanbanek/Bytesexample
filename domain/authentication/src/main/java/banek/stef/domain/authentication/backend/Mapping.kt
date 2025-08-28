package banek.stef.domain.authentication.backend

import banek.stef.api.authentication.AuthenticationParams
import banek.stef.api.authentication.User

internal fun AuthenticationParams.toAuthenticationRequest() = AuthenticationRequest(
    username = this.username,
    password = this.password
)

internal fun UserResponse.toUser() = User(
    name = this.name,
    email = this.email
)