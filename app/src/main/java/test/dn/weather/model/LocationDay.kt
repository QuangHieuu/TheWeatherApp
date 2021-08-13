package test.dn.weather.model

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import test.dn.weather.R
import test.dn.weather.utils.NumberUtils

@Entity
data class LocationDay(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Long = 0,

    @ColumnInfo(name = "weather_state_name")
    @SerializedName("weather_state_name")
    @Expose
    var stateName: String = "",

    @ColumnInfo(name = "the_temp")
    @SerializedName("the_temp")
    @Expose
    var theTemp: Float = 0f,

    @ColumnInfo(name = "humidity")
    @SerializedName("humidity")
    @Expose
    var humidity: Float = 0f,

    @ColumnInfo(name = "predictability")
    @SerializedName("predictability")
    @Expose
    var predictability: Float = 0f,

    @ColumnInfo(name = "applicable_date")
    @SerializedName("applicable_date")
    @Expose
    var date: String = "",

    @ColumnInfo(name = "weather_state_abbr")
    @SerializedName("weather_state_abbr")
    @Expose
    var stateAbbr: String = "",
) {
    companion object {
        val DiffUtil_CALLBACK: DiffUtil.ItemCallback<LocationDay> =
            object : DiffUtil.ItemCallback<LocationDay>() {
                override fun areItemsTheSame(oldItem: LocationDay, newItem: LocationDay): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: LocationDay,
                    newItem: LocationDay
                ): Boolean = oldItem.predictability == newItem.predictability ||
                        oldItem.theTemp == newItem.theTemp ||
                        oldItem.stateName == newItem.stateName ||
                        oldItem.stateAbbr == newItem.stateAbbr
            }
    }
}

@BindingAdapter("setWeatherTemp")
fun setWeatherTemp(view: AppCompatTextView, temp: Float) {
    val newText = NumberUtils.formatDoublePercent(temp)
    val text = SpannableString(newText).apply {
        setSpan(
            AbsoluteSizeSpan(view.textSize.toInt()),
            0,
            newText.length,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
    }
    val cel = view.resources.getString(R.string.text_celsius)
    val percentSign = SpannableString(cel).apply {
        setSpan(
            AbsoluteSizeSpan(view.resources.getDimensionPixelSize(R.dimen.dp_60)),
            cel.length - 1,
            cel.length,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
    }
    view.text = TextUtils.concat(text, percentSign)
}

@BindingAdapter("setTextPercent")
fun setTextPercent(view: AppCompatTextView, percent: Float = 0f) {
    val newText = NumberUtils.formatDoublePercent(percent)
    val text = SpannableString(newText).apply {
        setSpan(
            AbsoluteSizeSpan(view.resources.getDimensionPixelSize(R.dimen.dp_18)),
            0,
            newText.length,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
    }

    val percentSign = SpannableString("%").apply {
        setSpan(
            AbsoluteSizeSpan(view.resources.getDimensionPixelSize(R.dimen.dp_12)),
            0,
            1,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
    }
    view.text = TextUtils.concat(text, percentSign)
}
