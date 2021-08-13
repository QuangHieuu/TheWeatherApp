package test.dn.weather.utils

import java.text.DecimalFormat

object NumberUtils {

    fun formatDoublePercent(value: Float): String {
        return DecimalFormat("0").format(value).replace(",", ".")
    }
}