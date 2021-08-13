package test.dn.weather.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Location(
    @Expose
    @SerializedName("consolidated_weather")
    val weather: ArrayList<LocationDay>,
    @Expose
    @SerializedName("time")
    val time: String
)
