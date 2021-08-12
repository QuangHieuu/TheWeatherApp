package test.dn.weather.data.remote.repository

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import test.dn.weather.data.remote.service.ApiService
import test.dn.weather.model.LocationDay
import test.dn.weather.utils.extension.checkInternet

interface LocationDayRepository {

    fun getLocationDay(woeid: String, date: String): Single<ArrayList<LocationDay>>
}

class LocationDayImpl constructor(
    private val api: ApiService
) : LocationDayRepository {
    override fun getLocationDay(woeid: String, date: String): Single<ArrayList<LocationDay>> {
        return api.getLocationDay(woeid, date)
            .checkInternet()
            .subscribeOn(Schedulers.io())
    }
}
