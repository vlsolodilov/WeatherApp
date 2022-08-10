package com.solodilov.weatherapp.data.repository

import com.solodilov.weatherapp.data.datasource.LocationDataSource
import com.solodilov.weatherapp.data.datasource.WeatherDataSource
import com.solodilov.weatherapp.data.datasource.WeatherDatabaseDataSource
import com.solodilov.weatherapp.data.mapper.WeatherMapper
import com.solodilov.weatherapp.domain.entity.Location
import com.solodilov.weatherapp.domain.repository.WeatherRepository
import com.solodilov.weatherapp.domain.entity.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val localDataSource: WeatherDatabaseDataSource,
    private val remoteDataSource: WeatherDataSource,
    private val locationDataSource: LocationDataSource,
    private val mapper: WeatherMapper,
) : WeatherRepository {

    override suspend fun getWeatherInfo(): Flow<WeatherInfo?> =
        localDataSource.getWeatherInfoDb().map { weatherInfoDb ->
            weatherInfoDb?.let {
                mapper.mapWeatherInfoDbToWeatherInfo(it)
            }
        }

    override suspend fun refreshWeatherInfo(location: String) =
        withContext(Dispatchers.IO) {
            val weatherInfoDto = remoteDataSource.getWeatherInfoDtoByCityName(location)
            localDataSource.insertWeatherInfoDb(mapper.mapWeatherInfoDtoToWeatherInfoDb(
                weatherInfoDto))
            locationDataSource.insertLocationDb(mapper.mapWeatherInfoDtoToLocationDb(weatherInfoDto))
        }

    override fun getLocationList(): Flow<List<Location>> =
        locationDataSource.getLocationListDb().map { locationDbList ->
            locationDbList.map { locationDb ->
                mapper.mapLocationDbToLocation(locationDb)
            }
        }

    override suspend fun deleteLocationById(id: Long) =
        locationDataSource.deleteLocationDbById(id)

    override suspend fun deleteAllLocation() =
        locationDataSource.deleteAllLocationDb()
}