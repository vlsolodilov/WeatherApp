package com.solodilov.weatherapp.data.datasource

import com.solodilov.weatherapp.data.datasource.database.entity.LocationWithTimeDb
import kotlinx.coroutines.flow.Flow

interface LocationDataSource {

    suspend fun insertLocationDb(locationWithTimeDb: LocationWithTimeDb)
    fun getLocationListDb(): Flow<List<LocationWithTimeDb>>
    suspend fun deleteLocationDbById(id: Long)
    suspend fun deleteAllLocationDb()
}