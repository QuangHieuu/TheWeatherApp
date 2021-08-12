package test.dn.weather.data.remote.repository.authentication

import test.base.architecture.model.Token
import test.dn.weather.utils.extension.notNull
import test.dn.weather.data.local.sharedPrefs.SharedPrefsApi
import test.dn.weather.data.local.sharedPrefs.SharedPrefsKey


interface TokenRepository {

    fun getToken(): Token?

    fun saveToken(token: Token)

    fun clearToken()

    fun isHasLogIn(): Boolean

    fun isVerifyEmail(): Boolean

    fun isVerifyPhone(): Boolean

    fun doLogout()
}

class TokenRepositoryImpl
constructor(private val sharedPrefsApi: SharedPrefsApi) : TokenRepository {
    private var tokenCache: Token? = null

    override fun getToken(): Token? {
        tokenCache.notNull {
            return it
        }

        val token = sharedPrefsApi.get(SharedPrefsKey.KEY_TOKEN, Token::class.java)
        token.notNull {
            tokenCache = it
            return it
        }

        return null
    }

    override fun saveToken(token: Token) {
        tokenCache = token
        sharedPrefsApi.put(SharedPrefsKey.KEY_TOKEN, tokenCache)
    }

    override fun clearToken() {
        tokenCache = null
        sharedPrefsApi.clear()
    }

    override fun isHasLogIn(): Boolean {
        return !getToken()?.accessToken.isNullOrEmpty() && isVerifyEmail() && isVerifyPhone()
    }

    override fun isVerifyEmail(): Boolean {
        getToken().notNull {
            return it.emailVerified
        }
        return false
    }

    override fun isVerifyPhone(): Boolean {
        getToken().notNull {
            return it.phoneVerified
        }
        return false
    }

    override fun doLogout() {
        clearToken()
    }

    companion object {
        const val USER_ROLE = "user/"
    }
}