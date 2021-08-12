package test.dn.weather.data.remote.service

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import test.dn.weather.data.remote.api.body.LoginBody

interface ApiService {

    @POST("auth/login")
    fun login(@Body body: LoginBody): Single<String>
}