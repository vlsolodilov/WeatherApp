package com.solodilov.weatherapp.domain.entity

import java.util.*

data class DailyForecast(
    val date: Date,
    val maxTemp: Double,
    val minTemp: Double,
    val iconCondition: String,
)