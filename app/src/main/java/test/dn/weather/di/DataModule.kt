package test.dn.weather.di

import android.content.Context
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import test.dn.weather.R
import test.dn.weather.data.local.room.AppDatabase
import test.dn.weather.data.local.room.dao.LocationDayDao
import test.dn.weather.data.local.room.impl.LocationDayDbImpl

val dataModule = module {
    single { provideAppDatabase(androidContext()) }
    single { provideLocationDayDao(get()) }
    single { provideLocationDayDbImpl(get()) }
}

fun provideAppDatabase(context: Context) = Room.databaseBuilder(
    context,
    AppDatabase::class.java,
    context.getString(R.string.app_name)
).fallbackToDestructiveMigration().build()

fun provideLocationDayDao(db: AppDatabase) = db.locationDayDao()

fun provideLocationDayDbImpl(locationDayDao: LocationDayDao) = LocationDayDbImpl(locationDayDao)