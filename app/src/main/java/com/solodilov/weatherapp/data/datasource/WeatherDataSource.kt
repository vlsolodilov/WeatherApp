package com.solodilov.weatherapp.data.datasource

import com.solodilov.weatherapp.data.model.WeatherInfoDto

interface WeatherDataSource {

    suspend fun getWeatherInfoDtoByCityName(cityName: String): WeatherInfoDto
}