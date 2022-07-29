package com.solodilov.weatherapp.domain.entity

import java.util.*

data class HourlyForecast(
    val time: Date,
    val temp: Double,
    val iconCondition: String,
)
