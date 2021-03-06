package test.dn.weather.base

import android.view.View
import androidx.annotation.StringRes
import io.reactivex.disposables.Disposable


/**
 * @author LongHV.
 */

interface BaseView {

    fun showLoading()
    fun hideLoading()
    fun onError(@StringRes resId: Int)
    fun handleApiError(apiError: Throwable, view: View)
    fun launchDisposable(job: () -> Disposable)

    // Showing dialog popup customize

    fun showToastSuccess(message: String)
}
