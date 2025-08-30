package banek.stef.core.di

import banek.stef.core.navigation.Navigator
import banek.stef.core.navigation.NavigatorImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val navigatorModule = module {
    singleOf(::NavigatorImpl) bind Navigator::class
}