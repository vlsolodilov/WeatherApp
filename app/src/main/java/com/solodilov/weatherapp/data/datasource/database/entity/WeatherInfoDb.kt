package com.solodilov.weatherapp.data.datasource.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WeatherInfoDb(
    @Embedded
    val locationDb: LocationDb,
    @Relation(
        parentColumn = "id",
        entityColumn = "locationId"
    )
    val dailyForecastListDb: List<DailyForecastDb>,
    @Relation(
        parentColumn = "id",
        entityColumn = "locationId"
    )
    val hourlyForecastListDb: List<HourlyForecastDb>,
)
