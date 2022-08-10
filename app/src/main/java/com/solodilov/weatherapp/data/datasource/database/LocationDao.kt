package com.solodilov.weatherapp.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.solodilov.weatherapp.data.datasource.database.entity.LocationWithTimeDb
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(locationWithTimeDb: LocationWithTimeDb)

    @Query("SELECT * FROM locationWithTimeDb ORDER BY time DESC")
    fun getLocationList(): Flow<List<LocationWithTimeDb>>

    @Query("DELETE FROM locationWithTimeDb  WHERE id =:id")
    suspend fun deleteLocationById(id: Long)

    @Query("DELETE FROM locationWithTimeDb")
    suspend fun deleteAllLocation()

}