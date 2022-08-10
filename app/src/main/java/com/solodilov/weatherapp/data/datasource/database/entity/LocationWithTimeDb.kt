package com.solodilov.weatherapp.data.datasource.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(value = ["cityName", "regionName"], unique = true),
        Index(value = ["latitude", "longitude"], unique = true)
    ]
)
data class LocationWithTimeDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long = UNDEFINED_ID,
    val cityName: String,
    val regionName: String,
    val latitude: Double,
    val longitude: Double,
    val time: Int,
) {
    companion object {
        const val UNDEFINED_ID = 0L
    }
}