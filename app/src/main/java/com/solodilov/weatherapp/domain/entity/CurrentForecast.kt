package com.solodilov.weatherapp.domain.entity

data class CurrentForecast(
    val cityName: String,
    val regionName: String,
    val temp: Double,
    val condition: String,
    val iconCondition: String,
    val feelsLikeTemp: Double,
)