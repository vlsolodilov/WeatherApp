package com.solodilov.weatherapp.di

import com.solodilov.weatherapp.data.datasource.WeatherDataSource
import com.solodilov.weatherapp.data.datasource.WeatherDataSourceImpl
import com.solodilov.weatherapp.data.repository.WeatherRepositoryImpl
import com.solodilov.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataModule {

	@Singleton
	@Binds
	fun bindWeatherDataSource(impl: WeatherDataSourceImpl): WeatherDataSource

	@Singleton
	@Binds
	fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

}