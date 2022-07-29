package com.solodilov.weatherapp.domain.usecase

import com.solodilov.weatherapp.domain.entity.WeatherInfo
import com.solodilov.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherInfoUseCase @Inject constructor(private val weatherRepository: WeatherRepository){

    suspend operator fun invoke(cityName: String): WeatherInfo =
        weatherRepository.getWeatherInfoByCityName(cityName)

}