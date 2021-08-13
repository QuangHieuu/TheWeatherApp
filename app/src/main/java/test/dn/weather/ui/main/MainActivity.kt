package test.dn.weather.ui.main

import android.util.Log
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

        mViewModel.getAllData()
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
        }
    }

    override fun onSubscribeObserver() {
        with(mViewModel) {
            listData.observe(this@MainActivity, {
                mainAdapter.submitList(it)
                setLocationDay(it[0])
            })

            position.observe(this@MainActivity, {
                mainAdapter.setCurrent(it)
            })

            locationDay.observe(this@MainActivity, {
                binding.locationDay = it
            })
        }
    }

    override fun registerOnClick() {
    }
}