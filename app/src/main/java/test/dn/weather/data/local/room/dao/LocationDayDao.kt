package test.dn.weather.data.local.room.dao

import androidx.room.*
import test.dn.weather.model.LocationDay

@Dao
interface LocationDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: ArrayList<LocationDay>)

    @Query("SELECT * FROM LocationDay ORDER BY applicable_date ASC")
    fun getAll(): List<LocationDay>

    @Query("DELETE FROM LocationDay")
    fun clearAll()
}