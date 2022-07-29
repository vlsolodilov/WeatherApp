package com.solodilov.weatherapp.data.datasource.network

import com.solodilov.weatherapp.data.model.WeatherInfoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    private companion object {
        const val API_KEY = "8017edc8c386422c8e9112309222707"
        const val DAYS = "3"
    }

    @GET("v1/forecast.json?key=$API_KEY&days=$DAYS&aqi=no&alerts=no")
    suspend fun getWeatherInfoDtoByCityName(@Query("q") cityName: String): WeatherInfoDto
}