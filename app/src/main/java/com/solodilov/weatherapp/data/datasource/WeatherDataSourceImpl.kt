package com.solodilov.weatherapp.data.datasource

import com.solodilov.weatherapp.data.model.WeatherInfoDto
import com.solodilov.weatherapp.data.datasource.network.WeatherApi
import javax.inject.Inject

class WeatherDataSourceImpl @Inject constructor(
    private val api: WeatherApi,
): WeatherDataSource {

    override suspend fun getWeatherInfoDtoByCityName(cityName: String): WeatherInfoDto =
        api.getWeatherInfoDtoByCityName(cityName)

}