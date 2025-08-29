package banek.stef.bytesexample

import android.app.Application
import banek.stef.domain.authentication.authenticationModule
import banek.stef.domain.cookies.cookiesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BytesExampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@BytesExampleApplication)

            modules(
                authenticationModule,
                cookiesModule
            )
        }
    }
}