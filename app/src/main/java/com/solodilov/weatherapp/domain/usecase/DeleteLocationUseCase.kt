package com.solodilov.weatherapp.domain.usecase

import com.solodilov.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class DeleteLocationUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(id: Long) =
        weatherRepository.deleteLocationById(id)
}