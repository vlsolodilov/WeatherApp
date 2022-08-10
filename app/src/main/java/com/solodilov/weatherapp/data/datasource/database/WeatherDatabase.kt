package com.solodilov.weatherapp.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.solodilov.weatherapp.data.datasource.database.entity.DailyForecastDb
import com.solodilov.weatherapp.data.datasource.database.entity.HourlyForecastDb
import com.solodilov.weatherapp.data.datasource.database.entity.LocationDb
import com.solodilov.weatherapp.data.datasource.database.entity.LocationWithTimeDb

@Database(
    entities = [
        LocationDb::class,
        HourlyForecastDb::class,
        DailyForecastDb::class,
        LocationWithTimeDb::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateConverter::class
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun locationDao(): LocationDao

    companion object {

        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        WeatherDatabase::class.java,
                        "weather_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}