package test.dn.weather.di

import android.app.Application
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import test.dn.weather.data.local.sharedPrefs.SharedPrefsImpl
import test.dn.weather.data.remote.repository.LocationDayImpl
import test.dn.weather.data.remote.repository.LocationDayRepository
import test.dn.weather.data.remote.service.ApiService
import test.dn.weather.data.remote.repository.authentication.TokenRepository
import test.dn.weather.data.remote.repository.authentication.TokenRepositoryImpl

val repositoryModule = module {
    single { provideTokenRepository(androidApplication()) }
    single { provideLocationDayRepository(get()) }
}

fun provideTokenRepository(app: Application): TokenRepository {
    return TokenRepositoryImpl(SharedPrefsImpl(app))
}

fun provideLocationDayRepository(api: ApiService): LocationDayRepository {
    return LocationDayImpl(api)
}