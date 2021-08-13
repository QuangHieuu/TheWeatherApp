package test.dn.weather.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import test.dn.weather.Environment
import test.dn.weather.base.BaseViewModel
import test.dn.weather.data.local.room.impl.LocationDayDbImpl
import test.dn.weather.data.remote.repository.LocationDayRepository
import test.dn.weather.utils.extension.loading

class SplashVM(
    private val locationRepo: LocationDayRepository,
    private val db: LocationDayDbImpl,
) : BaseViewModel() {

    private val _loading by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean> = _loading

    private val _success by lazy { MutableLiveData<Boolean>() }
    val toMainActivity: LiveData<Boolean> = _success

    private val _handleError by lazy { MutableLiveData<Throwable>() }
    val handleError: LiveData<Throwable> = _handleError
    fun getLocationDay() {
        launchDisposable {
            locationRepo
                .getLocation(Environment.WOEID)
                .loading(_loading)
                .subscribe({
                    db.clearAll()
                    db.insertAll(it.weather)
                    _success.postValue(true)
                }, {
                    _handleError.postValue(it)
                })
        }
    }
}