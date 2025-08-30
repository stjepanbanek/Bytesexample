package banek.stef.domain.authentication

import banek.stef.api.authentication.AuthenticationApi
import banek.stef.domain.authentication.backend.AuthenticationBackend
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authenticationModule = module {
    factoryOf(::AuthenticationBackend)

    singleOf(::AuthenticationApiImpl) bind AuthenticationApi::class
}