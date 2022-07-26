package com.solodilov.weatherapp.data.datasource.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long = UNDEFINED_ID,
    val cityName: String,
    val regionName: String,
    val latitude: Double,
    val longitude: Double,
) {
    companion object {
        const val UNDEFINED_ID = 0L
    }
}