package com.solodilov.weatherapp.data.datasource

import com.solodilov.weatherapp.data.datasource.database.LocationDao
import com.solodilov.weatherapp.data.datasource.database.entity.LocationWithTimeDb
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationDataSourceImpl @Inject constructor(
    private val dao: LocationDao,
): LocationDataSource {

    override suspend fun insertLocationDb(locationWithTimeDb: LocationWithTimeDb) =
        dao.insertLocation(locationWithTimeDb)

    override fun getLocationListDb(): Flow<List<LocationWithTimeDb>> =
        dao.getLocationList()

    override suspend fun deleteLocationDbById(id: Long) =
        dao.deleteLocationById(id)

    override suspend fun deleteAllLocationDb() =
        dao.deleteAllLocation()

}