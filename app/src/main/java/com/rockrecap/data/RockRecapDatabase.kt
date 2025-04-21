package com.rockrecap.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Route::class], version = 2, exportSchema = false)
abstract class RockRecapDatabase : RoomDatabase() {
    abstract fun routeDao(): RouteDao

    companion object {
        @Volatile
        private var instance: RockRecapDatabase? = null

        fun getDatabase(context: Context): RockRecapDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, RockRecapDatabase::class.java, "route_database")
                    .fallbackToDestructiveMigration()  // Add the fallback for destructive migration
                    .build()
                    .also { instance = it }
            }
        }
    }
}