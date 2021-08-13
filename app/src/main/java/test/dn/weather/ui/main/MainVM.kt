package test.dn.weather.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import test.dn.weather.base.BaseViewModel
import test.dn.weather.data.local.room.impl.LocationDayDbImpl
import test.dn.weather.model.LocationDay

class MainVM(
    private val impl: LocationDayDbImpl
) : BaseViewModel() {

    private val _locationDay by lazy { MutableLiveData<LocationDay>() }
    val locationDay: LiveData<LocationDay> = _locationDay

    private val _position by lazy { MutableLiveData<Int>() }
    val position: LiveData<Int> = _position

    private val _listData by lazy { MutableLiveData<List<LocationDay>>() }
    val listData: LiveData<List<LocationDay>> = _listData

    fun getAllData() {
        launchDisposable {
            Single.create<List<LocationDay>> { it.onSuccess(impl.getAll()) }
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _listData.postValue(it)
                }, {

                })
        }
    }

    fun setCurrentPosition(position: Int) {
        _position.postValue(position)
    }

    fun setLocationDay(locationDay: LocationDay) {
        _locationDay.postValue(locationDay)
    }
}