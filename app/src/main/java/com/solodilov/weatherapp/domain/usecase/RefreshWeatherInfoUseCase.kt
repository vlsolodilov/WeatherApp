package com.solodilov.weatherapp.domain.usecase

import com.solodilov.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class RefreshWeatherInfoUseCase @Inject constructor(private val weatherRepository: WeatherRepository){

    suspend operator fun invoke(cityName: String) =
        weatherRepository.refreshWeatherInfo(cityName)

}