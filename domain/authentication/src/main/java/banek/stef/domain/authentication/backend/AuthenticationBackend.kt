package banek.stef.domain.authentication.backend

import kotlinx.coroutines.delay

internal class AuthenticationBackend {

    suspend fun login(request: AuthenticationRequest): Result<UserResponse> {
        delay(200)

        return if (request.username == "stef" && request.password == "1234") {
            Result.success(UserResponse(id = "1", name = "Stef", email = "stef@infinum.com"))
        } else {
            Result.failure(Exception("Invalid credentials"))
        }
    }
}