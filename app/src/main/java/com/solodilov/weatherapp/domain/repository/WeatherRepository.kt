package com.solodilov.weatherapp.domain.repository

import com.solodilov.weatherapp.domain.entity.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeatherInfo(): Flow<WeatherInfo>
    suspend fun refreshWeatherInfo(newLocation: String?)
}