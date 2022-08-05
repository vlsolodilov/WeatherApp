package com.solodilov.weatherapp.data.datasource.database

import androidx.room.*
import com.solodilov.weatherapp.data.datasource.database.entity.DailyForecastDb
import com.solodilov.weatherapp.data.datasource.database.entity.HourlyForecastDb
import com.solodilov.weatherapp.data.datasource.database.entity.LocationDb
import com.solodilov.weatherapp.data.datasource.database.entity.WeatherInfoDb
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertLocation(locationDb: LocationDb): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertHourlyForecastList(hourlyForecastDbList: List<HourlyForecastDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertDailyForecastList(dailyForecastDbList: List<DailyForecastDb>)

    @Transaction
    open suspend fun insertWeatherInfoDb(weatherInfoDb: WeatherInfoDb) {
        clear()
        val locationId = insertLocation(weatherInfoDb.locationDb)

        weatherInfoDb.hourlyForecastListDb.forEach { hourlyForecastDb ->
            hourlyForecastDb.locationId = locationId
        }
        insertHourlyForecastList(weatherInfoDb.hourlyForecastListDb)

        weatherInfoDb.dailyForecastListDb.forEach { dailyForecastDb ->
            dailyForecastDb.locationId = locationId
        }
        insertDailyForecastList(weatherInfoDb.dailyForecastListDb)
    }

    @Transaction
    @Query("SELECT * FROM LocationDb")
    abstract fun getWeatherInfoDb(): Flow<WeatherInfoDb>

    @Query("DELETE FROM LocationDb")
    abstract suspend fun clear()
}