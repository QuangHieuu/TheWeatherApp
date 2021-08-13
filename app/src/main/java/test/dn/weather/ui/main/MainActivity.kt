package test.dn.weather.ui.main

import android.view.View
import android.view.animation.AnimationUtils
import org.koin.androidx.viewmodel.ext.android.viewModel
import test.dn.weather.R
import test.dn.weather.base.BaseActivity
import test.dn.weather.databinding.ActivityMainBinding
import test.dn.weather.model.LocationDay


class MainActivity : BaseActivity<ActivityMainBinding, MainVM>() {
    override val layoutID: Int = R.layout.activity_main

    override val mViewModel: MainVM by viewModel()

    private lateinit var mainAdapter: DayAdapter

    override fun initialize() {
        initView()
    }

    private fun initView() {
        with(binding) {
            mainAdapter = DayAdapter(object : DayAdapter.OnItemClick {
                override fun onClick(position: Int, locationDay: LocationDay) {
                    mViewModel.apply {
                        setCurrentPosition(position)
                        setLocationDay(locationDay)
                    }
                }
            })
            rcvDate.apply {
                setHasFixedSize(true)
                adapter = mainAdapter
            }
            refreshLayout.setOnRefreshListener {
                mViewModel.getLocationDay()
            }
        }
    }

    override fun onSubscribeObserver() {
        with(mViewModel) {
            loading.observe(this@MainActivity, {
                binding.refreshLayout.isRefreshing = it
            })

            listData.observe(this@MainActivity, {
                if (it.isNotEmpty()) {
                    mainAdapter.submitList(it)
                    setLocationDay(it[0])
                }
                binding.visibility = it.isNotEmpty()
            })

            position.observe(this@MainActivity, {
                mainAdapter.setCurrent(it)
            })

            locationDay.observe(this@MainActivity, {
                binding.locationDay = it
                animationIcon()
            })

            handleError.observe(this@MainActivity, { handleApiError(it, binding.refreshLayout) })
        }
    }

    override fun registerOnClick() {
    }

    fun animationIcon() {
        AnimationUtils.loadAnimation(this, R.anim.animate_slide_down_fade_in)
            .also { hyperspaceJumpAnimation ->
                binding.imgWeatherIcon.startAnimation(hyperspaceJumpAnimation)
            }
    }

    fun showHide(hide: Boolean) {
        binding.txtNoData.visibility = if (hide) View.GONE else View.VISIBLE
        with(if (hide) View.GONE else View.VISIBLE) {
            binding.let {
                it.imgWeatherIcon.visibility = this
                it.txtTemp.visibility = this
                it.txtWeatherState.visibility = this
                it.txtDateTime.visibility = this
                it.imgWeatherIcon.visibility = this
            }
        }
    }
}