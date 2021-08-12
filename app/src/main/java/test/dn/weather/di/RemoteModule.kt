package test.dn.weather.di

import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import test.dn.weather.BuildConfig
import test.dn.weather.Environment
import test.dn.weather.data.remote.api.middleware.InterceptorImpl
import test.dn.weather.data.remote.api.middleware.RxErrorHandlingCallAdapterFactory
import test.dn.weather.data.remote.service.ApiService
import test.dn.weather.data.remote.repository.authentication.TokenRepository
import java.util.concurrent.TimeUnit

/**
 * @author Leopold
 */
private const val CONNECT_TIMEOUT = 30L
private const val WRITE_TIMEOUT = 30L
private const val READ_TIMEOUT = 30L

val remoteModule = module {
    single { provideInterceptor(get()) }
    single { provideOkHttpClient(get(), get()) }
    single { provideRetrofit(get(), get()) }
    single { provideNameApi(get()) }
}

fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Environment.BASE_URL + Environment.API_PREFIX)
        .addCallAdapterFactory(
            RxErrorHandlingCallAdapterFactory.create()
        )
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideInterceptor(tokenRepository: TokenRepository): Interceptor {
    return InterceptorImpl(
        tokenRepository
    )
}

fun provideOkHttpClient(cache: Cache, interceptor: Interceptor): OkHttpClient {
    val httpClientBuilder = OkHttpClient.Builder()
    httpClientBuilder.cache(cache)
    httpClientBuilder.addInterceptor(interceptor)

    httpClientBuilder.readTimeout(
        NetworkConstants.READ_TIMEOUT, TimeUnit.SECONDS
    )
    httpClientBuilder.writeTimeout(
        NetworkConstants.WRITE_TIMEOUT, TimeUnit.SECONDS
    )
    httpClientBuilder.connectTimeout(
        NetworkConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS
    )

    if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor()
        httpClientBuilder.addInterceptor(logging)
        logging.level = HttpLoggingInterceptor.Level.BODY
    }

    return httpClientBuilder.build()
}


fun provideNameApi(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

object NetworkConstants {
    const val READ_TIMEOUT: Long = 30
    const val WRITE_TIMEOUT: Long = 30
    const val CONNECTION_TIMEOUT: Long = 30
}