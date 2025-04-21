package com.rockrecap.data

import androidx.room.Query
import com.rockrecap.data.enums.RouteType
import kotlinx.coroutines.flow.Flow

class OfflineRouteRepository(private val routeDao: RouteDao) : RoutesRepository {

    // methods used in the core of editing and modifying routes
    override fun getAllActiveRoutesStream(): Flow<List<Route>> = routeDao.getAllActiveRoutes()
    override fun getAllInactiveRoutesStream(): Flow<List<Route>> = routeDao.getAllInactiveRoutes()
    override fun getAllRoutesStream(): Flow<List<Route>> = routeDao.getAllRoutes()
    override fun getRouteStream(id: Int): Flow<Route?> = routeDao.getRoute(id)
    override suspend fun updateCompletedStatus(route: Route, newStatus: String) = routeDao.updateCompletedStatus(route.routeId, newStatus)
    override suspend fun updateActiveStatus(route: Route, newStatus: String) = routeDao.updateActiveStatus(route.routeId, newStatus)
    override suspend fun updateRouteTime(route: Route, newTime: Int) = routeDao.updateRouteTime(route.routeId, newTime)
    override suspend fun insertRoute(route: Route) = routeDao.insert(route)
    override suspend fun deleteRoute(route: Route) = routeDao.delete(route)
    override suspend fun updateRoute(route: Route) = routeDao.update(route)

    // functions used for all route type statistics on the statistics page
    override suspend fun getTotalCompletedCount(): Int = routeDao.getTotalCompletedCount()
    override suspend fun getTotalAttemptedCount(): Int = routeDao.getTotalAttemptedCount()
    override suspend fun getTotalClimbingTime(): Int = routeDao.getTotalClimbingTime()
    override suspend fun getShortestClimbingTime(): Int = routeDao.getShortestCompletedClimbingTime()
    override suspend fun getLongestClimbingTime(): Int = routeDao.getLongestCompletedClimbingTime()

    // methods called for summary statistics for each type of route
    override suspend fun getCompletedCountByRouteType(routeTypeString: String): Int = routeDao.getCompletedCountByRouteType(routeTypeString)
    override suspend fun getAttemptedCountByRouteType(routeTypeString: String): Int = routeDao.getAttemptedCountByRouteType(routeTypeString)
    override suspend fun getClimbingTimeByRouteType(routeTypeString: String): Int = routeDao.getClimbingTimeByRouteType(routeTypeString)
    override suspend fun getShortestCompletedClimbingTimeByRouteType(routeTypeString: String): Int = routeDao.getShortestCompletedClimbingTimeByRouteType(routeTypeString)
    override suspend fun getLongestCompletedClimbingTimeByRouteType(routeTypeString: String): Int = routeDao.getLongestCompletedClimbingTimeByRouteType(routeTypeString)

    // methods for finding amount of completed routes per grade type
    override suspend fun getCompletedCountByGradeAndRouteType(gradeString: String, routeTypeString: String): Int = routeDao.getCompletedCountByGradeAndRouteType(gradeString, routeTypeString)
    override suspend fun getIncompleteCountByGradeAndRouteType(gradeString: String, routeTypeString: String): Int = routeDao.getCompletedCountByGradeAndRouteType(gradeString, routeTypeString)
}
