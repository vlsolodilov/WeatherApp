package com.solodilov.weatherapp.data.model

data class WeatherInfoDto(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)