package banek.stef.domain.cookies

import banek.stef.domain.cookies.api.CookieApi
import banek.stef.domain.cookies.api.CookieApiImpl
import banek.stef.domain.cookies.service.CookieServiceImpl
import banek.stef.domain.cookies.service.CookiesService
import banek.stef.domain.cookies.ui.home.HomeViewModel
import banek.stef.domain.cookies.ui.login.LoginViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val cookiesModule = module {
    factoryOf(::CookieApiImpl) bind CookieApi::class

    singleOf(::CookieServiceImpl) bind CookiesService::class

    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
}