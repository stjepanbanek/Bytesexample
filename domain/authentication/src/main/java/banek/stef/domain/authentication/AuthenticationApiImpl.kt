package banek.stef.domain.authentication

import banek.stef.api.authentication.AuthenticationApi
import banek.stef.api.authentication.AuthenticationParams
import banek.stef.api.authentication.User
import banek.stef.domain.authentication.backend.AuthenticationBackend
import banek.stef.domain.authentication.backend.toAuthenticationRequest
import banek.stef.domain.authentication.backend.toUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class AuthenticationApiImpl(
    private val backend: AuthenticationBackend
) : AuthenticationApi {

    private val userFlow = MutableStateFlow<User?>(null)

    override val currentUser: Flow<User?> = userFlow.asStateFlow()

    override suspend fun login(params: AuthenticationParams): Result<User> {
        val result = backend.login(params.toAuthenticationRequest()).map { it.toUser() }
        result.onSuccess { userResponse ->
            userFlow.value = userResponse
        }
        return result
    }
}