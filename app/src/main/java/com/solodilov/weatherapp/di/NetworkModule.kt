package com.solodilov.weatherapp.di

import com.solodilov.weatherapp.data.datasource.network.WeatherApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

	private companion object {

		const val BASE_URL = "https://api.weatherapi.com/"
	}

	@Provides
	fun provideGsonConverterFactory(): GsonConverterFactory =
		GsonConverterFactory.create()

	@Provides
	@Singleton
	fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
	): Retrofit =
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(gsonConverterFactory)
			.build()

	@Provides
	@Singleton
	fun provideLoginApi(
		retrofit: Retrofit,
	): WeatherApi =
		retrofit.create(WeatherApi::class.java)
}