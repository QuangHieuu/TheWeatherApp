package test.dn.weather.data.remote.repository

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import test.dn.weather.data.remote.service.ApiService
import test.dn.weather.model.Location
import test.dn.weather.model.LocationDay
import test.dn.weather.utils.extension.checkInternet

interface LocationDayRepository {

    fun getLocationDay(woeid: String, date: String): Single<ArrayList<LocationDay>>
    fun getLocation(woeid: String): Observable<Location>
}

class LocationDayImpl constructor(
    private val api: ApiService
) : LocationDayRepository {
    override fun getLocationDay(woeid: String, date: String): Single<ArrayList<LocationDay>> {
        return api.getLocationDay(woeid, date)
            .checkInternet()
            .subscribeOn(Schedulers.io())
    }

    override fun getLocation(woeid: String): Observable<Location> = api.getLocation(woeid)
        .checkInternet()
        .subscribeOn(Schedulers.io())
}
