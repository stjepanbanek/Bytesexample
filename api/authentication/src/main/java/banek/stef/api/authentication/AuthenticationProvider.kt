package banek.stef.api.authentication

import kotlinx.coroutines.flow.Flow

interface AuthenticationProvider {

    val currentUser: Flow<User?>

    suspend fun login(params: AuthenticationParams): Result<User>
}