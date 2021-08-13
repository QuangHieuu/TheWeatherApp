package test.dn.weather.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import test.dn.weather.R
import test.dn.weather.data.remote.api.error.RetrofitException
import test.dn.weather.data.remote.api.error.Type
import test.dn.weather.utils.extension.notNull
import test.dn.weather.utils.extension.showToast
import java.net.HttpURLConnection

@SuppressLint("Registered")
abstract class BaseActivity<AC : ViewDataBinding, VM : BaseViewModel> :
    AppCompatActivity(), BaseView {

    protected lateinit var binding: AC
    protected abstract val layoutID: Int
    protected abstract val mViewModel: VM

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutID)
        binding.lifecycleOwner = this
        initialize()
        baseObserver()
        onSubscribeObserver()
        registerOnClick()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
    }

    protected abstract fun initialize()

    protected abstract fun onSubscribeObserver()

    protected abstract fun registerOnClick()

    private fun baseObserver() {
        mViewModel.isLoadingLive.observe(this, {
            if (it) showLoading() else hideLoading()
        })
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showToastSuccess(message: String) {
    }


    override fun onError(@StringRes resId: Int) {

    }

    override fun handleApiError(apiError: Throwable) {
        if (apiError is RetrofitException) {
            if (apiError.getErrorType() == Type.NETWORK) {
                showToast(apiError.getMsgError())
                return
            }

            if (apiError.getErrorCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                showToast(apiError.getMsgError())
                return
            }

            val msgError = apiError.getMsgError()
            if (msgError != null) {
                showToast(msgError)
                return
            }

            val allMsgError = apiError.getAllMsgError(this)
            if (allMsgError != null) {
                showToast(allMsgError)
                return
            }
        }
    }

    override fun launchDisposable(job: () -> Disposable) {
        compositeDisposable.add(job())
    }

    fun transactionSlideOutTopToBottom() {
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down)
    }

    fun transactionSlideInBottomToTop() {
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation)
    }

    fun transactionSlideOutRightToLeft() {
        overridePendingTransition(R.anim.no_animation, R.anim.slide_out_right)
    }
}