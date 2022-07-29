package com.solodilov.weatherapp.domain.entity

data class WeatherInfo(
    val currentForecast: CurrentForecast,
    val dailyForecastList: List<DailyForecast>,
    val hourlyForecastList: List<HourlyForecast>,
)
