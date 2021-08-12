package test.dn.weather.ui.splash

import org.koin.androidx.viewmodel.ext.android.viewModel
import test.dn.weather.R
import test.dn.weather.base.BaseActivity
import test.dn.weather.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashVM>() {

    override val layoutID: Int = R.layout.activity_splash

    override val mViewModel: SplashVM by viewModel()

    override fun initialize() {
        mViewModel.getLocationDay("2021/07/31")
    }

    override fun onSubscribeObserver() {

        mViewModel.handleError.observe(this, { handleApiError(it) })
    }

    override fun registerOnClick() {
    }
}