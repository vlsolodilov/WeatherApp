package com.solodilov.weatherapp.domain.repository

import com.solodilov.weatherapp.domain.entity.Location
import com.solodilov.weatherapp.domain.entity.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeatherInfo(): Flow<WeatherInfo?>
    suspend fun refreshWeatherInfo(location: String)

    fun getLocationList(): Flow<List<Location>>
    suspend fun deleteLocationById(id: Long)
    suspend fun deleteAllLocation()
}