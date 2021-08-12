package test.dn.weather.data.remote.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import test.dn.weather.model.LocationDay
import java.util.*

interface ApiService {

    @GET("location/{woeid}/{date}/")
    fun getLocationDay(
        @Path("woeid") woeid: String,
        @Path("date") date: String
    ): Single<ArrayList<LocationDay>>
}