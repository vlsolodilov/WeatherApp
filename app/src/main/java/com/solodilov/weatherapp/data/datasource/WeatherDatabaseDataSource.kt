package com.solodilov.weatherapp.data.datasource

import com.solodilov.weatherapp.data.datasource.database.entity.WeatherInfoDb
import kotlinx.coroutines.flow.Flow

interface WeatherDatabaseDataSource {

    suspend fun insertWeatherInfoDb(weatherInfoDb: WeatherInfoDb)
    fun getWeatherInfoDb(): Flow<WeatherInfoDb?>
}