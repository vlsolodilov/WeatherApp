package com.solodilov.weatherapp.di

import android.app.Application
import com.solodilov.weatherapp.data.datasource.WeatherDataSource
import com.solodilov.weatherapp.data.datasource.WeatherDataSourceImpl
import com.solodilov.weatherapp.data.datasource.WeatherDatabaseDataSource
import com.solodilov.weatherapp.data.datasource.WeatherDatabaseDataSourceImpl
import com.solodilov.weatherapp.data.datasource.database.WeatherDao
import com.solodilov.weatherapp.data.datasource.database.WeatherDatabase
import com.solodilov.weatherapp.data.repository.WeatherRepositoryImpl
import com.solodilov.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataModule {

	@Singleton
	@Binds
	fun bindWeatherDatabaseDataSource(impl: WeatherDatabaseDataSourceImpl): WeatherDatabaseDataSource

	@Singleton
	@Binds
	fun bindWeatherDataSource(impl: WeatherDataSourceImpl): WeatherDataSource

	@Singleton
	@Binds
	fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

	companion object {

		@Singleton
		@Provides
		fun provideWeatherDatabase(application: Application): WeatherDatabase {
			return WeatherDatabase.getInstance(application)
		}

		@Singleton
		@Provides
		fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao {
			return weatherDatabase.weatherDao()
		}
	}
}