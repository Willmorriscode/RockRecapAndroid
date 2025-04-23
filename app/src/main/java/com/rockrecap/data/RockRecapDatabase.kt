package com.rockrecap.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rockrecap.data.enums.RouteActiveStatus
import com.rockrecap.data.enums.RouteColor
import com.rockrecap.data.enums.RouteCompleteStatus
import com.rockrecap.data.enums.RouteGrade
import com.rockrecap.data.enums.RouteType
import com.rockrecap.utilities.getSeedRoutes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(entities = [Route::class], version = 2, exportSchema = false)
abstract class RockRecapDatabase : RoomDatabase() {
    abstract fun routeDao(): RouteDao

    companion object {
        @Volatile
        private var instance: RockRecapDatabase? = null

        fun getDatabase(context: Context): RockRecapDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, RockRecapDatabase::class.java, "route_database")
                    .fallbackToDestructiveMigration(false)
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            // this will run once when building the application, hits a function with a bunch of defined routes for use in the application
                            CoroutineScope(Dispatchers.IO).launch {
                                getDatabase(context).routeDao().insertRoutes(getSeedRoutes())
                            }
                        }
                    })
                    .build()
                    .also { instance = it }
            }
        }
    }
}