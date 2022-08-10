package com.solodilov.weatherapp.domain.usecase

import com.solodilov.weatherapp.domain.entity.Location
import com.solodilov.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationListUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    operator fun invoke(): Flow<List<Location>> =
        weatherRepository.getLocationList()
}