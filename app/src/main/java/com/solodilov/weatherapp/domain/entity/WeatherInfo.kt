package com.solodilov.weatherapp.domain.entity

data class WeatherInfo(
    val location: Location,
    val dailyForecastList: List<DailyForecast>,
    val hourlyForecastList: List<HourlyForecast>,
)
