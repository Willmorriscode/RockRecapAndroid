package com.rockrecap.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rockrecap.data.enums.RouteActiveStatus
import com.rockrecap.data.enums.RouteGrade
import com.rockrecap.data.enums.RouteType
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteDao {

    /* Methods used creating or updating routes in the database */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(route: Route)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(route: Route)

    @Query("UPDATE routes SET activeStatus = :newStatus WHERE routeId = :routeId")
    suspend fun updateActiveStatus(routeId: Int, newStatus: String)

    @Query("UPDATE routes SET completedStatus = :newStatus WHERE routeId = :routeId")
    suspend fun updateCompletedStatus(routeId: Int, newStatus: String)

    @Query("UPDATE routes SET timeLogged = :newTime WHERE routeId = :routeId")
    suspend fun updateRouteTime(routeId: Int, newTime: Int)

    @Delete
    suspend fun delete(route: Route)

    /* Methods for reading Route(s) from the database */

    @Query("SELECT * from routes WHERE routeId = :id")
    fun getRoute(id: Int): Flow<Route>

    @Query("SELECT * from routes ORDER BY startDate ASC")
    fun getAllRoutes(): Flow<List<Route>>

    @Query("SELECT * from routes WHERE activeStatus = :status ORDER BY startDate ASC")
    fun getAllActiveRoutes(status: String = RouteActiveStatus.ACTIVE.name): Flow<List<Route>>

    @Query("SELECT * from routes WHERE activeStatus = :status ORDER BY startDate ASC")
    fun getAllInactiveRoutes(status: String = RouteActiveStatus.INACTIVE.name): Flow<List<Route>>


    /* Methods for reading Route(s) from the database on the summary page for all route types */

    @Query("SELECT COUNT(*) FROM routes WHERE completedStatus = 'COMPLETED'")
    suspend fun getTotalCompletedCount(): Int

    @Query("SELECT COUNT(*) FROM routes") // represents the total amount of routes logged overall
    suspend fun getTotalAttemptedCount(): Int

    @Query("SELECT SUM(timeLogged) FROM routes")
    suspend fun getTotalClimbingTime(): Int

    @Query("SELECT MIN(timeLogged) FROM routes WHERE completedStatus = 'COMPLETED'")
    suspend fun getShortestCompletedClimbingTime(): Int

    @Query("SELECT MAX(timeLogged) FROM routes WHERE completedStatus = 'COMPLETED'")
    suspend fun getLongestCompletedClimbingTime(): Int

    /* Methods to get summary statistics based on the RouteType */

    @Query("SELECT COUNT(*) FROM routes WHERE completedStatus = 'COMPLETED' AND type = :routeTypeString")
    suspend fun getCompletedCountByRouteType(routeTypeString: String): Int

    @Query("SELECT COUNT(*) FROM routes WHERE type = :routeTypeString") // represents the total amount of routes logged overall
    suspend fun getAttemptedCountByRouteType(routeTypeString: String): Int

    @Query("SELECT SUM(timeLogged) FROM routes WHERE type = :routeTypeString")
    suspend fun getClimbingTimeByRouteType(routeTypeString: String): Int

    @Query("SELECT MIN(timeLogged) FROM routes WHERE completedStatus = 'COMPLETED' AND type = :routeTypeString")
    suspend fun getShortestCompletedClimbingTimeByRouteType(routeTypeString: String): Int

    @Query("SELECT MAX(timeLogged) FROM routes WHERE completedStatus = 'COMPLETED' AND type = :routeTypeString")
    suspend fun getLongestCompletedClimbingTimeByRouteType(routeTypeString: String): Int

    /* Route summary method for all grades in Boudlering */

    @Query("SELECT COUNT(*) FROM routes WHERE completedStatus = 'COMPLETED' AND type = :routeTypeString AND grade = :gradeString")
    suspend fun getCompletedCountByGradeAndRouteType(gradeString: String, routeTypeString: String): Int

    @Query("SELECT COUNT(*) FROM routes WHERE completedStatus = 'INCOMPLETE' AND type = :routeTypeString AND grade = :gradeString")
    suspend fun getIncompleteCountByGradeAndRouteType(gradeString: String, routeTypeString: String): Int
}