package banek.stef.domain.cookies

import banek.stef.domain.cookies.ui.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val cookiesModule = module {

    viewModelOf(::LoginViewModel)
}