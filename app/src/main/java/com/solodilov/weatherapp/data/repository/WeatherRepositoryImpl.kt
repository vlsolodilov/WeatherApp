package com.solodilov.weatherapp.data.repository

import com.solodilov.weatherapp.data.datasource.WeatherDataSource
import com.solodilov.weatherapp.data.mapper.WeatherMapper
import com.solodilov.weatherapp.domain.repository.WeatherRepository
import com.solodilov.weatherapp.domain.entity.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val dataSource: WeatherDataSource,
    private val mapper: WeatherMapper,
) : WeatherRepository {

    override suspend fun getWeatherInfoByCityName(cityName: String): WeatherInfo =
        mapper.mapWeatherInfoDtoToWeatherInfo(dataSource.getWeatherInfoDtoByCityName(cityName))
}