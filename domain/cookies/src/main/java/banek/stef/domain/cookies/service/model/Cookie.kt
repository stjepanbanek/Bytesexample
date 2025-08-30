package banek.stef.domain.cookies.service.model

internal data class Cookie(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val status: CookieStatus,
)

internal enum class CookieStatus {
    FAVORITE,
    LOADING,
    NOT_FAVORITE;

    fun toggle(): CookieStatus {
        return when (this) {
            FAVORITE -> NOT_FAVORITE
            NOT_FAVORITE -> FAVORITE
            LOADING -> LOADING
        }
    }
}
