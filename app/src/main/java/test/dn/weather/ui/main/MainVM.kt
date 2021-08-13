package test.dn.weather.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import test.dn.weather.Environment
import test.dn.weather.base.BaseViewModel
import test.dn.weather.data.local.room.impl.LocationDayDbImpl
import test.dn.weather.data.remote.repository.LocationDayRepository
import test.dn.weather.model.LocationDay
import test.dn.weather.utils.extension.loading

class MainVM(
    private val locationRepo: LocationDayRepository,
    private val impl: LocationDayDbImpl
) : BaseViewModel() {
    var listData: LiveData<List<LocationDay>> = impl.getAll()

    private val _locationDay by lazy { MutableLiveData<LocationDay>() }
    val locationDay: LiveData<LocationDay> = _locationDay

    private val _position by lazy { MutableLiveData<Int>() }
    val position: LiveData<Int> = _position

    fun setCurrentPosition(position: Int) {
        _position.postValue(position)
    }

    fun setLocationDay(locationDay: LocationDay) {
        _locationDay.postValue(locationDay)
    }

    private val _loading by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean> = _loading

    private val _handleError by lazy { MutableLiveData<Throwable>() }
    val handleError: LiveData<Throwable> = _handleError
    fun getLocationDay() {
        launchDisposable {
            locationRepo
                .getLocation(Environment.WOEID)
                .loading(_loading)
                .subscribe({
                    impl.clearAll()
                    impl.insertAll(it.weather)
                    setCurrentPosition(0)
                    setLocationDay(it.weather[0])
                }, {
                    _handleError.postValue(it)
                })
        }
    }
}