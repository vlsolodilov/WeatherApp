package com.solodilov.weatherapp.data.datasource.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = LocationDb::class,
        parentColumns = ["id"],
        childColumns = ["locationId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class HourlyForecastDb(
    @PrimaryKey(autoGenerate = true)
    var id: Long = UNDEFINED_ID,
    val time: Date,
    val temp: Double,
    val condition: String,
    val iconCondition: String,
    val feelsLikeTemp: Double,
    var locationId: Long = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0L
    }
}
