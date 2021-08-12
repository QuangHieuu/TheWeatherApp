package test.dn.weather.constant

import java.net.HttpURLConnection

object Constants {
    val HTTP_VALIDATION_ERROR: Int = 422
    val HTTP_BAD_REQUEST_ERROR: Int = 400

    val SHARDED_PRE_LONG_DEFAULT: Long = 0L
    val SHARDED_PRE_INT_DEFAULT: Int = 0
    val SHARDED_PRE_FLOAT_DEFAULT: Float = 0f
    val SHARDED_PRE_BOOLEAN_DEFAULT: Boolean = false
    val EMPTY: String = ""
    val TEST_PASSWORD: String = "the123123"
    val TEST_EMAIL: String = "nguyenminhthespkt@gmail.com"
    val CODE_REFRESH_TOKEN: Int = 401
    const val HTTP_UNAUTHORIZED = HttpURLConnection.HTTP_UNAUTHORIZED
    const val EXTRA_ARGS = "EXTRA_ARGS"
    const val SETTING_NOTIFICATION_DISABLE = 2
    const val SETTING_NOTIFICATION_ENABLE = 1
    const val API_PREFIX = "api/"
}
