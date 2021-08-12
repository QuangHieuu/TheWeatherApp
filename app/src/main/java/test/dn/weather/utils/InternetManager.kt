package test.dn.weather.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import test.dn.weather.R
import test.dn.weather.base.BaseApp
import test.dn.weather.data.remote.api.error.RetrofitException
import test.dn.weather.data.remote.api.error.Type

class InternetManager {

    companion object {
        fun isConnected(): Boolean {
            val connectivityManager =
                BaseApp.sInstance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
    }
}

class InternetException : RetrofitException(
    Type.NETWORK,
    Exception(BaseApp.sInstance.getString(R.string.text_internet_error))
)