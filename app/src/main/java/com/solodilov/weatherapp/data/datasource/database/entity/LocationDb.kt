package com.solodilov.weatherapp.data.datasource.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["cityName", "regionName"], unique = true)]
)
data class LocationDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long = UNDEFINED_ID,
    val cityName: String,
    val regionName: String,
) {
    companion object {
        const val UNDEFINED_ID = 0L
    }
}