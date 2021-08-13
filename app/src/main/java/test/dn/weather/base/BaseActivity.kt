package test.dn.weather.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import test.dn.weather.R
import test.dn.weather.data.remote.api.error.RetrofitException
import test.dn.weather.data.remote.api.error.Type
import test.dn.weather.utils.extension.showSnackBar
import test.dn.weather.widget.ProgressDialog
import java.net.HttpURLConnection

@SuppressLint("Registered")
abstract class BaseActivity<AC : ViewDataBinding, VM : BaseViewModel> :
    AppCompatActivity(), BaseView {

    protected lateinit var binding: AC
    protected abstract val layoutID: Int
    protected abstract val mViewModel: VM

    private val compositeDisposable = CompositeDisposable()
    private lateinit var progress: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutID)
        binding.lifecycleOwner = this
        progress = ProgressDialog(this)
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
        progress.show()
    }

    override fun hideLoading() {
        progress.dismiss()
    }

    override fun showToastSuccess(message: String) {
    }


    override fun onError(@StringRes resId: Int) {

    }

    override fun handleApiError(apiError: Throwable, view: View) {
        if (apiError is RetrofitException) {
            if (apiError.getErrorType() == Type.NETWORK) {
                showSnackBar(apiError.getMsgError(), view)
                return
            }

            if (apiError.getErrorCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                showSnackBar(apiError.getMsgError(), view)
                return
            }

            val msgError = apiError.getMsgError()
            if (msgError != null) {
                showSnackBar(msgError, view)
                return
            }

            val allMsgError = apiError.getAllMsgError(this)
            if (allMsgError != null) {
                showSnackBar(allMsgError, view)
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