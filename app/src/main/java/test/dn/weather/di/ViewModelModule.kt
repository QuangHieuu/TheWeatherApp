package test.dn.weather.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import test.dn.weather.ui.main.MainVM
import test.dn.weather.ui.splash.SplashVM

val viewModelModule = module {
    viewModel { SplashVM(get(), get()) }
    viewModel { MainVM(get(), get()) }
}