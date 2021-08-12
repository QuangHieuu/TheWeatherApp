package test.dn.weather.ui.splash

import android.content.Intent
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import test.dn.weather.R
import test.dn.weather.base.BaseActivity
import test.dn.weather.databinding.ActivitySplashBinding
import test.dn.weather.ui.main.MainActivity
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashVM>() {

    override val layoutID: Int = R.layout.activity_splash

    override val mViewModel: SplashVM by viewModel()

    override fun initialize() {

        Single.create<String> { emitter ->
            emitter.onSuccess("")
        }.delay(2, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).doAfterSuccess {
            startActivity(Intent(this, MainActivity::class.java).apply {
                flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or
                            Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }.subscribe()
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }
}