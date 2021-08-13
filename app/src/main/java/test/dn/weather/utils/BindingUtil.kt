package test.dn.weather.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import test.dn.weather.Environment
import test.dn.weather.widget.ArcProgress

@BindingAdapter("arc_progress")
fun setArcPercent(view: ArcProgress, percent: Float = 0f) {
    view.setProgress(percent)
}

@BindingAdapter("setWeatherIcon")
fun setWeatherIcon(
    view: AppCompatImageView,
    abbr: String?
) {
    val url = Environment.BASE_URL + Environment.IMAGE_PREFIX + "png/64/${abbr}.png"
    Glide.with(view)
        .asBitmap()
        .load(url)
        .into(view)
}

@BindingAdapter("setTextFullDate")
fun setTextFullDate(view: AppCompatTextView, date: String?) {
    if (date != null) {
        view.text = TimeConvert.formatDate(date, formatOutput = "EEE MM dd, YYYY")
    }
}
