package test.dn.weather.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
class LocationDay {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Long = 0

    @ColumnInfo(name = "weather_state_name")
    @SerializedName("weather_state_name")
    @Expose
    var stateName: String = ""

    @ColumnInfo(name = "the_temp")
    @SerializedName("the_temp")
    @Expose
    var theTemp: Float = 0f

    @ColumnInfo(name = "humidity")
    @SerializedName("humidity")
    @Expose
    var humidity: Float = 0f

    @ColumnInfo(name = "predictability")
    @SerializedName("predictability")
    @Expose
    var predictability: Int = 0
}