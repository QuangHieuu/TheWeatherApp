package test.dn.weather.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationDay(
    @PrimaryKey val id: Int
)