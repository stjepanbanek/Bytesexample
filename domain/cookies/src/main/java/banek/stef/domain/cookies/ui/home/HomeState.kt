package banek.stef.domain.cookies.ui.home

import banek.stef.domain.cookies.service.model.Cookie

internal sealed interface HomeState {
    data object Loading : HomeState
    data object Error : HomeState
    data class Content(val cookies: List<Cookie>, val userName: String) : HomeState
}