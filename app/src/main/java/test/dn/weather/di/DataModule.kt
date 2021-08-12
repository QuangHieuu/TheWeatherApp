package test.dn.weather.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.dsl.module
import test.dn.weather.R

val dataModule = module {
    single { roomDataBaseBuilder(get()) }
}

fun roomDataBaseBuilder(applicationContext: Application) = Room.databaseBuilder(
    applicationContext,
    RoomDatabase::class.java,
    applicationContext.getString(R.string.app_name)
).build()
