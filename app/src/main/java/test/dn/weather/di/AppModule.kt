package test.dn.weather.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.dsl.module
import test.base.architecture.data.remote.api.middleware.BooleanAdapter
import test.base.architecture.data.remote.api.middleware.DoubleAdapter
import test.base.architecture.data.remote.api.middleware.IntegerAdapter
import test.dn.weather.utils.extension.BaseSchedulerProvider
import test.dn.weather.utils.extension.SchedulerProvider

val appModule = module {
    single { provideBaseSchedulerProvider() }
    single { provideGson() }
}

fun provideBaseSchedulerProvider(): BaseSchedulerProvider {
    return SchedulerProvider()
}

fun provideGson(): Gson {
    val booleanAdapter = BooleanAdapter()
    val integerAdapter = IntegerAdapter()
    val doubleAdapter = DoubleAdapter()
    return GsonBuilder()
        .registerTypeAdapter(Boolean::class.java, booleanAdapter)
        .registerTypeAdapter(Int::class.java, integerAdapter)
        .registerTypeAdapter(Double::class.java, doubleAdapter)
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .create()
}