package com.solodilov.weatherapp.data.mapper

import com.solodilov.weatherapp.data.model.WeatherInfoDto
import com.solodilov.weatherapp.domain.entity.Location
import com.solodilov.weatherapp.domain.entity.DailyForecast
import com.solodilov.weatherapp.domain.entity.HourlyForecast
import com.solodilov.weatherapp.domain.entity.WeatherInfo
import com.solodilov.weatherapp.Converter
import com.solodilov.weatherapp.data.datasource.database.entity.DailyForecastDb
import com.solodilov.weatherapp.data.datasource.database.entity.HourlyForecastDb
import com.solodilov.weatherapp.data.datasource.database.entity.LocationDb
import com.solodilov.weatherapp.data.datasource.database.entity.WeatherInfoDb
import javax.inject.Inject

class WeatherMapper @Inject constructor() {

    fun mapWeatherInfoDbToWeatherInfo(weatherInfoDb: WeatherInfoDb): WeatherInfo =
        WeatherInfo(
            location = Location(
                cityName = weatherInfoDb.locationDb.cityName,
                regionName = weatherInfoDb.locationDb.regionName,
            ),
            dailyForecastList = mapWeatherInfoDbToDailyForecastList(weatherInfoDb),
            hourlyForecastList = mapWeatherInfoDbToHourlyForecastList(weatherInfoDb)
        )

    private fun mapWeatherInfoDbToDailyForecastList(weatherInfoDb: WeatherInfoDb): List<DailyForecast> =
        weatherInfoDb.dailyForecastListDb.map { forecastDay ->
            DailyForecast(
                date = forecastDay.date,
                maxTemp = forecastDay.maxTemp,
                minTemp = forecastDay.minTemp,
                iconCondition = forecastDay.iconCondition
            )
        }

    private fun mapWeatherInfoDbToHourlyForecastList(weatherInfoDb: WeatherInfoDb): List<HourlyForecast> =
        weatherInfoDb.hourlyForecastListDb.map { forecastHour ->
                HourlyForecast(
                    time = forecastHour.time,
                    temp = forecastHour.temp,
                    condition = forecastHour.condition,
                    iconCondition = forecastHour.iconCondition,
                    feelsLikeTemp = forecastHour.feelsLikeTemp
                )

        }

    fun mapWeatherInfoDtoToWeatherInfoDb(weatherInfoDto: WeatherInfoDto): WeatherInfoDb =
        WeatherInfoDb(
            locationDb = LocationDb(
                cityName = weatherInfoDto.location.name,
                regionName = weatherInfoDto.location.region,
            ),
            dailyForecastListDb = mapWeatherInfoDtoToDailyForecastListDb(weatherInfoDto),
            hourlyForecastListDb = mapWeatherInfoDtoToHourlyForecastListDb(weatherInfoDto)
        )

    private fun mapWeatherInfoDtoToDailyForecastListDb(weatherInfoDto: WeatherInfoDto): List<DailyForecastDb> =
        weatherInfoDto.forecast.forecastday.map { forecastDay ->
            DailyForecastDb(
                date = Converter.getDate(forecastDay.date),
                maxTemp = forecastDay.day.maxtemp_c,
                minTemp = forecastDay.day.mintemp_c,
                iconCondition = forecastDay.day.condition.icon
            )
        }

    private fun mapWeatherInfoDtoToHourlyForecastListDb(weatherInfoDto: WeatherInfoDto): List<HourlyForecastDb> =
        weatherInfoDto.forecast.forecastday.flatMap { forecastDay ->
            forecastDay.hour.map { forecastHour ->
                HourlyForecastDb(
                    time = Converter.getTime(forecastHour.time),
                    temp = forecastHour.temp_c,
                    condition = forecastHour.condition.text,
                    iconCondition = forecastHour.condition.icon,
                    feelsLikeTemp = forecastHour.feelslike_c
                )
            }
        }
}