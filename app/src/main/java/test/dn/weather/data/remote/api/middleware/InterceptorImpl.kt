package test.dn.weather.data.remote.api.middleware

import androidx.annotation.NonNull
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import test.base.architecture.utils.extension.insert
import test.dn.weather.utils.extension.notNull
import test.dn.weather.BuildConfig
import test.dn.weather.Environment
import test.dn.weather.data.remote.repository.authentication.TokenRepository
import java.io.IOException
import java.util.*


class InterceptorImpl(private var tokenRepository: TokenRepository?) : Interceptor {

    companion object {
        private const val TAG = "InterceptorImpl"
        private const val TOKEN_TYPE = "Bearer "
        private const val KEY_TOKEN = "Authorization"

        const val CODE_REFRESH_TOKEN: Int = 401
    }

    private var mIsRefreshToken: Boolean = false

    @Throws(IOException::class)
    override fun intercept(@NonNull chain: Interceptor.Chain): Response {
        val builder = initializeHeader(chain)
        var request = builder.build()
        var response = chain.proceed(request)

        if (response.code == 401) {
            val resStr = response.body?.string().toString()
            val json = JSONObject(resStr)
            val code = json.getInt("code")
            if (code == CODE_REFRESH_TOKEN) {
                tokenRepository?.getToken()?.accessToken.notNull { accessToken ->
                    TODO("Refresh Token ")
                }
            }
        }
        return response
    }

    private fun initializeHeader(chain: Interceptor.Chain): Request.Builder {
        val originRequest = chain.request()

        val urlWithPrefix = Environment.BASE_URL + Environment.API_PREFIX
        val newUrl = chain.request().url.toString().insert(
            index = urlWithPrefix.length,
            contentInsert = ""
        )

        val builder = originRequest.newBuilder()
            .url(newUrl)
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .addHeader("version", BuildConfig.VERSION_NAME)
            .addHeader("platform", "android")
            .addHeader("Accept-Language", Locale.getDefault().getLanguage())
            .method(originRequest.method, originRequest.body)

        tokenRepository?.getToken()?.accessToken.notNull { accessToken ->
            builder.addHeader(KEY_TOKEN, TOKEN_TYPE + accessToken)
        }
        return builder
    }
}