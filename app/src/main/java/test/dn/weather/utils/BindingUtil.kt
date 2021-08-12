package test.dn.weather.utils

import android.text.SpannableString
import android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import test.dn.weather.R
import test.dn.weather.widget.ArcProgress


@BindingAdapter("arc_progress")
fun setArcPercent(view: ArcProgress, percent: Float = 0f) {
    view.setProgress(percent)
}

@BindingAdapter("setTextPercent")
fun setTextPercent(view: AppCompatTextView, percent: Float = 0f) {
    val text = SpannableString(percent.toString()).apply {
        setSpan(
            AbsoluteSizeSpan(view.resources.getDimensionPixelSize(R.dimen.dp_12)),
            0,
            percent.toString().length,
            SPAN_INCLUSIVE_INCLUSIVE
        )
    }

    val percentSign = SpannableString("%").apply {
        setSpan(
            AbsoluteSizeSpan(view.resources.getDimensionPixelSize(R.dimen.dp_10)),
            0,
            1,
            SPAN_INCLUSIVE_INCLUSIVE
        )
    }
    view.text = TextUtils.concat(text, percentSign)
}
