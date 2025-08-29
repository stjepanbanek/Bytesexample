package banek.stef.domain.cookies.ui.home

internal sealed interface HomeInteraction {
    object Logout : HomeInteraction
    data class CookieClicked(val cookieId: String) : HomeInteraction
}