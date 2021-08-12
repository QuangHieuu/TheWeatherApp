package test.dn.weather.base

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import test.dn.weather.di.appModule
import test.dn.weather.di.viewModelModule
import test.dn.weather.di.contextRequireModule
import test.dn.weather.di.dataModule
import test.dn.weather.di.remoteModule
import test.dn.weather.di.repositoryModule

class BaseApp : Application() {

    companion object {
        lateinit var sInstance: BaseApp
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        startKoin {
            androidLogger()
            androidContext(this@BaseApp)
            androidFileProperties()
            modules(
                appModule,
                appModule,
                viewModelModule,
                contextRequireModule,
                dataModule,
                remoteModule,
                repositoryModule
            )
        }

    }
}