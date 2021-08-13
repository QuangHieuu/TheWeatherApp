package test.dn.weather.ui.splash

import android.content.Intent
import org.koin.androidx.viewmodel.ext.android.viewModel
import test.dn.weather.R
import test.dn.weather.base.BaseActivity
import test.dn.weather.databinding.ActivitySplashBinding
import test.dn.weather.ui.main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashVM>() {

    override val layoutID: Int = R.layout.activity_splash

    override val mViewModel: SplashVM by viewModel()

    override fun initialize() {
        mViewModel.getLocationDay()
    }

    override fun onSubscribeObserver() {
        with(mViewModel) {
            loading.observe(this@SplashActivity, { if (it) showLoading() else hideLoading() })

            handleError.observe(this@SplashActivity, { handleApiError(it) })

            toMainActivity.observe(this@SplashActivity, {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java).apply {
                    flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                                Intent.FLAG_ACTIVITY_NEW_TASK
                })
            })
        }
    }

    override fun registerOnClick() {
    }
}