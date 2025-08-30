package banek.stef.domain.cookies.ui.home

import banek.stef.domain.cookies.service.model.Cookie

internal sealed interface HomeInteraction {
    data object LogoutClicked : HomeInteraction
    data object RetryClicked : HomeInteraction
    data class CookieClicked(val cookieId: String) : HomeInteraction
    data class FavoriteClicked(val cookie: Cookie) : HomeInteraction
}