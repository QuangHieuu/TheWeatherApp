package test.dn.weather.ui.main

import org.koin.androidx.viewmodel.ext.android.viewModel
import test.dn.weather.R
import test.dn.weather.base.BaseActivity
import test.dn.weather.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainVM>() {
    override val layoutID: Int = R.layout.activity_main

    override val mViewModel: MainVM by viewModel()

    override fun initialize() {
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }
}