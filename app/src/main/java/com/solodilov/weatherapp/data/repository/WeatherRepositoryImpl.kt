package com.solodilov.weatherapp.data.repository

import com.solodilov.weatherapp.data.datasource.WeatherDataSource
import com.solodilov.weatherapp.data.datasource.WeatherDatabaseDataSource
import com.solodilov.weatherapp.data.mapper.WeatherMapper
import com.solodilov.weatherapp.domain.repository.WeatherRepository
import com.solodilov.weatherapp.domain.entity.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val localDataSource: WeatherDatabaseDataSource,
    private val remoteDataSource: WeatherDataSource,
    private val mapper: WeatherMapper,
) : WeatherRepository {

    override suspend fun getWeatherInfo(): Flow<WeatherInfo> =
        localDataSource.getWeatherInfoDb().map { weatherInfoDb ->
            mapper.mapWeatherInfoDbToWeatherInfo(weatherInfoDb)
        }

    override suspend fun refreshWeatherInfo(newLocation: String?) =
        withContext(Dispatchers.IO) {
            val location = newLocation ?: localDataSource.getWeatherInfoDb().first().locationDb.cityName
            val weatherInfoDto = remoteDataSource.getWeatherInfoDtoByCityName(location)
            localDataSource.insertWeatherInfoDb(mapper.mapWeatherInfoDtoToWeatherInfoDb(
                weatherInfoDto))
        }
}