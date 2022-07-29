package com.solodilov.weatherapp.domain.repository

import com.solodilov.weatherapp.domain.entity.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherInfoByCityName(cityName: String): WeatherInfo
}