package com.rockrecap.data
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Route] from a data source.
 */
interface RoutesRepository {
    /**
     * Retrieve all the items from the given data source.
     */
    fun getAllRoutesStream(): Flow<List<Route>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getRouteStream(id: Int): Flow<Route?>

    /**
     * Retrieve an item from the given data source that matches the active route status.
     */
    fun getAllActiveRoutesStream(): Flow<List<Route>>

    /**
     * Retrieve an item from the given data source that matches the inactive route status.
     */
    fun getAllInactiveRoutesStream(): Flow<List<Route>>

    /**
     * Insert item in the data source
     */
    suspend fun insertRoute(route: Route)

    suspend fun insertRoutes(routes: List<Route>)

    /**
     * Delete item from the data source
     */
    suspend fun deleteRoute(route: Route)

    /**
     * Update item in the data source
     */

    suspend fun updateRoute(route: Route)

    suspend fun updateRouteTime(route: Route, newTime: Int)

    // updates the active status to be whatever the passed in value is
    suspend fun updateActiveStatus(route: Route, newStatus: String)

    // updates the c status to be whatever the passed in value is
    suspend fun updateCompletedStatus(route: Route, newStatus: String)

    // statistics page calls for all route types
    suspend fun getTotalCompletedCount(): Int
    suspend fun getTotalAttemptedCount(): Int
    suspend fun getTotalClimbingTime(): Int
    suspend fun getShortestClimbingTime(): Int
    suspend fun getLongestClimbingTime(): Int

    // Function calls that will retrieve summary information given a route type
    suspend fun getCompletedCountByRouteType(routeTypeString: String): Int
    suspend fun getAttemptedCountByRouteType(routeTypeString: String): Int
    suspend fun getClimbingTimeByRouteType(routeTypeString: String): Int
    suspend fun getShortestCompletedClimbingTimeByRouteType(routeTypeString: String): Int
    suspend fun getLongestCompletedClimbingTimeByRouteType(routeTypeString: String): Int

    // methods to get completed count for each grade type per route type
    suspend fun getCompletedCountByGradeAndRouteType(gradeString: String, routeTypeString: String): Int
    suspend fun getIncompleteCountByGradeAndRouteType(gradeString: String, routeTypeString: String): Int
}