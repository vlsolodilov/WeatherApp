package com.solodilov.weatherapp.data.mapper

import com.solodilov.weatherapp.data.model.WeatherInfoDto
import com.solodilov.weatherapp.domain.entity.CurrentForecast
import com.solodilov.weatherapp.domain.entity.DailyForecast
import com.solodilov.weatherapp.domain.entity.HourlyForecast
import com.solodilov.weatherapp.domain.entity.WeatherInfo
import com.solodilov.weatherapp.Converter
import javax.inject.Inject

class WeatherMapper @Inject constructor() {

    fun mapWeatherInfoDtoToWeatherInfo(weatherInfoDto: WeatherInfoDto): WeatherInfo =
        WeatherInfo(
            currentForecast = CurrentForecast(
                cityName = weatherInfoDto.location.name,
                temp = weatherInfoDto.current.temp_c,
                condition = weatherInfoDto.current.condition.text,
                iconCondition = weatherInfoDto.current.condition.icon,
                feelsLikeTemp = weatherInfoDto.current.feelslike_c
            ),
            dailyForecastList = mapWeatherInfoDtoToDailyForecastList(weatherInfoDto),
            hourlyForecastList = mapWeatherInfoDtoToHourlyForecastList(weatherInfoDto)
        )

    private fun mapWeatherInfoDtoToDailyForecastList(weatherInfoDto: WeatherInfoDto): List<DailyForecast> =
        weatherInfoDto.forecast.forecastday.map { forecastDay ->
            DailyForecast(
                date = Converter.getDate(forecastDay.date),
                maxTemp = forecastDay.day.maxtemp_c,
                minTemp = forecastDay.day.mintemp_c,
                iconCondition = forecastDay.day.condition.icon
            )
        }

    private fun mapWeatherInfoDtoToHourlyForecastList(weatherInfoDto: WeatherInfoDto): List<HourlyForecast> =
        weatherInfoDto.forecast.forecastday.flatMap { forecastDay ->
            forecastDay.hour.map { forecastHour ->
                HourlyForecast(
                    time = Converter.getTime(forecastHour.time),
                    temp = forecastHour.temp_c,
                    iconCondition = forecastHour.condition.icon
                )
            }
        }
}