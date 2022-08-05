package com.solodilov.weatherapp.data.datasource

import com.solodilov.weatherapp.data.datasource.database.WeatherDao
import com.solodilov.weatherapp.data.datasource.database.entity.WeatherInfoDb
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDatabaseDataSourceImpl @Inject constructor(
    private val dao: WeatherDao,
): WeatherDatabaseDataSource {

    override suspend fun insertWeatherInfoDb(weatherInfoDb: WeatherInfoDb) =
        dao.insertWeatherInfoDb(weatherInfoDb)

    override fun getWeatherInfoDb(): Flow<WeatherInfoDb> =
        dao.getWeatherInfoDb()

}