package com.solodilov.weatherapp.data.mapper

import com.solodilov.weatherapp.data.model.WeatherInfoDto
import com.solodilov.weatherapp.Converter
import com.solodilov.weatherapp.data.datasource.database.entity.*
import com.solodilov.weatherapp.domain.entity.*
import javax.inject.Inject

class WeatherMapper @Inject constructor() {

    fun mapWeatherInfoDbToWeatherInfo(weatherInfoDb: WeatherInfoDb): WeatherInfo =
        WeatherInfo(
            location = Location(
                id = weatherInfoDb.locationDb.id,
                cityName = weatherInfoDb.locationDb.cityName,
                regionName = weatherInfoDb.locationDb.regionName,
                latitude = weatherInfoDb.locationDb.latitude,
                longitude = weatherInfoDb.locationDb.longitude
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
                latitude = weatherInfoDto.location.lat,
                longitude = weatherInfoDto.location.lon,
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

    fun mapWeatherInfoDtoToLocationDb(weatherInfoDto: WeatherInfoDto): LocationWithTimeDb =
        LocationWithTimeDb(
            cityName = weatherInfoDto.location.name,
            regionName = weatherInfoDto.location.region,
            latitude = weatherInfoDto.location.lat,
            longitude = weatherInfoDto.location.lon,
            time = weatherInfoDto.location.localtime_epoch
        )

    fun mapLocationDbToLocation(locationWithTimeDb: LocationWithTimeDb): Location =
        Location(
            id = locationWithTimeDb.id,
            cityName = locationWithTimeDb.cityName,
            regionName = locationWithTimeDb.regionName,
            latitude = locationWithTimeDb.latitude,
            longitude = locationWithTimeDb.longitude
        )
}