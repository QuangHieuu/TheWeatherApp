package test.dn.weather.data.local.room.impl

import test.dn.weather.data.local.room.dao.LocationDayDao
import test.dn.weather.model.LocationDay

class LocationDayDbImpl(private val dao: LocationDayDao) {
    fun insertAll(list: ArrayList<LocationDay>) {
        dao.insertAll(list)
    }

    fun clearAll() {
        dao.clearAll()
    }
}