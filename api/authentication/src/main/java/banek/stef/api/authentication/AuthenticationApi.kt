package banek.stef.api.authentication

import kotlinx.coroutines.flow.Flow

interface AuthenticationApi {

    val currentUser: Flow<User?>

    suspend fun login(params: AuthenticationParams): Result<User>
}