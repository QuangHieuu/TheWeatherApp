package test.dn.weather.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import test.dn.weather.data.local.room.dao.LocationDayDao
import test.dn.weather.model.LocationDay

@Database(entities = [LocationDay::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract fun locationDayDao(): LocationDayDao
}