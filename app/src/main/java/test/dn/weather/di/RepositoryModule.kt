package test.dn.weather.di

import android.app.Application
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import test.dn.weather.repository.authentication.TokenRepository
import test.dn.weather.repository.authentication.TokenRepositoryImpl
import test.dn.weather.data.local.sharedPrefs.SharedPrefsImpl
import test.dn.weather.data.remote.service.ApiService
import test.dn.weather.repository.user.UserRemoteImpl
import test.dn.weather.repository.user.UserRepository

val repositoryModule = module {
    single { provideTokenRepository(androidApplication()) }
    single { provideUserRepository(get()) }
}

fun provideTokenRepository(app: Application): TokenRepository {
    return TokenRepositoryImpl(SharedPrefsImpl(app))
}

fun provideUserRepository(api: ApiService): UserRepository {
    return UserRemoteImpl(api)
}