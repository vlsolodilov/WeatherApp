package com.solodilov.weatherapp.domain.entity

data class Location(
    val id: Long,
    val cityName: String,
    val regionName: String,
    val latitude: Double,
    val longitude: Double,
)