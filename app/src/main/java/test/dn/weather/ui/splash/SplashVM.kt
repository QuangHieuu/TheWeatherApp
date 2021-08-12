package test.dn.weather.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.functions.Consumer
import test.dn.weather.Environment
import test.dn.weather.base.BaseViewModel
import test.dn.weather.data.remote.repository.LocationDayRepository

class SplashVM(
    private val locationRepo: LocationDayRepository
) : BaseViewModel() {

    private val _success by lazy { MutableLiveData<Boolean>() }
    val toMainActivity: LiveData<Boolean> = _success

    private val _handleError by lazy { MutableLiveData<Throwable>() }
    val handleError: LiveData<Throwable> = _handleError
    fun getLocationDay(date: String) {
        launchDisposable {
            locationRepo
                .getLocationDay(Environment.WOEID, date)
                .subscribe({
                    _success.postValue(true)
                }, {
                    _handleError.postValue(it)
                })
        }
    }
}