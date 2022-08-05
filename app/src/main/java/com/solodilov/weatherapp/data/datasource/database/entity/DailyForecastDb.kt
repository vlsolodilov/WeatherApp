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
data class DailyForecastDb(
    @PrimaryKey(autoGenerate = true)
    var id: Long = UNDEFINED_ID,
    val date: Date,
    val maxTemp: Double,
    val minTemp: Double,
    val iconCondition: String,
    var locationId: Long = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0L
    }
}