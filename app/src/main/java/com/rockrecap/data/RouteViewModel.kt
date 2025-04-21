package com.rockrecap.data

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rockrecap.data.enums.RouteCompleteStatus
import com.rockrecap.data.enums.RouteGrade
import com.rockrecap.data.enums.RouteType
import com.rockrecap.data.enums.getRouteGradeList
import com.rockrecap.utilities.floatToPercentage
import com.rockrecap.utilities.formatRouteTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RouteViewModel(private val routesRepository: RoutesRepository): ViewModel() {
    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }

    /* Active and inactive routes for viewing on the active and inactive routes pages */

    /**
     * Ui State for ActiveRoute
     */
    data class ActiveRouteUiState(val routeList: List<Route> = listOf())

    val activeRoutesUiState: StateFlow<ActiveRouteUiState> = routesRepository.getAllActiveRoutesStream()
        .map{ ActiveRouteUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ActiveRouteUiState()
        )

    /**
     * Ui State for InactiveRoute
     */
    data class InactiveRouteUiState(val routeList: List<Route> = listOf())

    val inactiveRoutesUiState: StateFlow<InactiveRouteUiState> = routesRepository.getAllInactiveRoutesStream()
        .map{ InactiveRouteUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = InactiveRouteUiState()
        )

    private val _selectedRouteId = MutableStateFlow<Int?>(null)
    val selectedRouteId: StateFlow<Int?> = _selectedRouteId.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedRoute: StateFlow<Route?> = _selectedRouteId
        .flatMapLatest { id ->
            id?.let { routesRepository.getRouteStream(it) } ?: flowOf(null)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )

    var routeSelected: Boolean = false

    /* Statistics page variable to track which route type we are viewing, null if no route type is selected */

    // Declare routeFilter as a MutableState
    private val _routeFilter = mutableStateOf<RouteType?>(null)
    val routeFilter: State<RouteType?> = _routeFilter

    fun updateRouteFilter(routeType: RouteType?) {
        _routeFilter.value = routeType
    }

    /* Backend calls for editing a route or adding a new route */

    fun setSelectedRoute(selectedRoute: Route){
        routeSelected = true
        _selectedRouteId.value = selectedRoute.routeId
    }

    fun getFilteredCompletedGradeStatistics(stats: UserRouteStatistics): List<GradeRouteCompletionStatistics>? {
        return when(routeFilter.value){
            RouteType.BOULDER -> stats.boulderGradeCompletionStats.boulderStats
            RouteType.TOP_ROPE -> stats.topRopeGradeCompletionStats.topRopeStats
            RouteType.LEAD_CLIMB -> stats.leadClimbGradeCompletionStats.leadClimbStats
            null -> null
        }
    }

    /* helper functions in the statistics page for formatting statistics*/

    fun getTotalClimbingTimeByRouteType(stats: UserRouteStatistics): String{
        val rawTime = when(routeFilter.value){
            RouteType.BOULDER -> stats.boulderCompletionStats.totalClimbingTime
            RouteType.TOP_ROPE -> stats.topRopeCompletionStats.totalClimbingTime
            RouteType.LEAD_CLIMB -> stats.leadClimbCompletionStats.totalClimbingTime
            null -> stats.totalCompletionStats.totalClimbingTime
        }
        return formatRouteTime(rawTime)
    }

    fun getShortestCompletedRouteTimeByRouteType(stats: UserRouteStatistics): String{
        val rawTime = when(routeFilter.value){
            RouteType.BOULDER -> stats.boulderCompletionStats.shortestCompletedClimbingTime
            RouteType.TOP_ROPE -> stats.topRopeCompletionStats.shortestCompletedClimbingTime
            RouteType.LEAD_CLIMB -> stats.leadClimbCompletionStats.shortestCompletedClimbingTime
            null -> stats.totalCompletionStats.shortestCompletedClimbingTime
        }
        return formatRouteTime(rawTime)
    }

    fun getLongestCompletedRouteTimeByRouteType(stats: UserRouteStatistics): String{
        val rawTime = when(routeFilter.value){
            RouteType.BOULDER -> stats.boulderCompletionStats.longestCompletedClimbingTime
            RouteType.TOP_ROPE -> stats.topRopeCompletionStats.longestCompletedClimbingTime
            RouteType.LEAD_CLIMB -> stats.leadClimbCompletionStats.longestCompletedClimbingTime
            null -> stats.totalCompletionStats.longestCompletedClimbingTime
        }
        return formatRouteTime(rawTime)
    }

    fun getRouteCompletionRateByRouteType(stats: UserRouteStatistics): Float{
        val completionRate = when(routeFilter.value){
            RouteType.BOULDER -> stats.boulderCompletionStats.completionRate
            RouteType.TOP_ROPE -> stats.topRopeCompletionStats.completionRate
            RouteType.LEAD_CLIMB -> stats.leadClimbCompletionStats.completionRate
            null -> stats.totalCompletionStats.completionRate
        }

        return completionRate
    }

    fun getRoutesCompletedByRouteType(stats: UserRouteStatistics): String{
        return when(routeFilter.value){
            RouteType.BOULDER -> stats.boulderCompletionStats.completed
            RouteType.TOP_ROPE -> stats.topRopeCompletionStats.completed
            RouteType.LEAD_CLIMB -> stats.leadClimbCompletionStats.completed
            null -> stats.totalCompletionStats.completed
        }.toString()
    }

    fun getRoutesAttemptedByRouteType(stats: UserRouteStatistics): String{
        return when(routeFilter.value){
            RouteType.BOULDER -> stats.boulderCompletionStats.attempted
            RouteType.TOP_ROPE -> stats.topRopeCompletionStats.attempted
            RouteType.LEAD_CLIMB -> stats.leadClimbCompletionStats.attempted
            null -> stats.totalCompletionStats.attempted
        }.toString()
    }

    suspend fun submitNewRoute(route: Route){
        routesRepository.insertRoute(route)
    }

    suspend fun updateRoute(editedRoute: Route) {
        routesRepository.updateRoute(editedRoute)
    }

    suspend fun deleteRoute(route: Route){
        routesRepository.deleteRoute(route)
    }

    suspend fun updateActiveStatus(route: Route, newStatus: String){
        routesRepository.updateActiveStatus(route, newStatus)
    }

    suspend fun updateCompletedStatus(route: Route, newStatus: String){
        routesRepository.updateCompletedStatus(route, newStatus)
    }

    suspend fun updateRouteTime(route: Route, timeToAdd: Int){
        routesRepository.updateRouteTime(route, (route.timeLogged + timeToAdd))
    }

    /* summary statistics for all route types */
    private suspend fun getTotalCompletedCount(): Int { return routesRepository.getTotalCompletedCount() }
    private suspend fun getTotalAttemptedCount(): Int { return routesRepository.getTotalAttemptedCount() }
    private suspend fun getTotalClimbingTime(): Int { return routesRepository.getTotalClimbingTime() }
    private suspend fun getShortestClimbingTime(): Int { return routesRepository.getShortestClimbingTime() }
    private suspend fun getLongestClimbingTime(): Int { return routesRepository.getLongestClimbingTime() }


    /* summary statistics that fetch based on the passed in route type */

    private suspend fun getCompletedCountByRouteType(routeTypeString: String): Int { return routesRepository.getCompletedCountByRouteType(routeTypeString) }
    private suspend fun getAttemptedCountByRouteType(routeTypeString: String): Int { return routesRepository.getAttemptedCountByRouteType(routeTypeString) }
    private suspend fun getClimbingTimeByRouteType(routeTypeString: String): Int { return routesRepository.getClimbingTimeByRouteType(routeTypeString) }
    private suspend fun getShortestCompletedClimbingTimeByRouteType(routeTypeString: String): Int { return routesRepository.getShortestCompletedClimbingTimeByRouteType(routeTypeString) }
    private suspend fun getLongestCompletedClimbingTimeByRouteType(routeTypeString: String): Int { return routesRepository.getLongestCompletedClimbingTimeByRouteType(routeTypeString) }

    /* Method to get number of completed routes for a route type given a grade type*/

    private suspend fun getCompletedCountByGradeAndRouteType(gradeString: String, routeTypeString: String): Int { return routesRepository.getCompletedCountByGradeAndRouteType(gradeString, routeTypeString) }
    private suspend fun getIncompleteCountByGradeAndRouteType(gradeString: String, routeTypeString: String): Int { return routesRepository.getIncompleteCountByGradeAndRouteType(gradeString, routeTypeString) }

    suspend fun getRouteCompletionStatusCountsByRouteType(routeType: RouteType, routeCompleteStatus: RouteCompleteStatus): List<Int> {

        val gradesCompletedList = mutableListOf<Int>()

        // getting the correct grades list based on which route type we are getting data for
        val routeGradeList: List<RouteGrade> = when(routeType){
            RouteType.BOULDER -> getRouteGradeList(1) // gets boulder grades
            RouteType.TOP_ROPE, RouteType.LEAD_CLIMB -> getRouteGradeList(2) // gets top rope / lead climb grades
        }

        for (grade in routeGradeList){
            when(routeCompleteStatus){
                RouteCompleteStatus.COMPLETED -> {
                    gradesCompletedList.add(getCompletedCountByGradeAndRouteType(grade.name, routeType.name))
                }
                RouteCompleteStatus.INCOMPLETE -> {
                    gradesCompletedList.add(getIncompleteCountByGradeAndRouteType(grade.name, routeType.name))
                }
            }
        }

        return gradesCompletedList
    }

    // will package up the database calls to put them into the correct userStatistics object
    private suspend fun assembleGradeCompletionStatisticsByRouteType(routeType: RouteType): List<GradeRouteCompletionStatistics> {

        // Each of these lists is just a list that contains the count for each grade type, and we have to associate
        val completeList = getRouteCompletionStatusCountsByRouteType(routeType, RouteCompleteStatus.COMPLETED)
        val incompleteList = getRouteCompletionStatusCountsByRouteType(routeType, RouteCompleteStatus.INCOMPLETE)

        val routeGradeList: List<RouteGrade> = when(routeType){
            RouteType.BOULDER -> getRouteGradeList(1) // gets boulder grades
            RouteType.TOP_ROPE, RouteType.LEAD_CLIMB -> getRouteGradeList(2) // gets top rope / lead climb grades
        }

        val routeStats = mutableListOf<GradeRouteCompletionStatistics>()

        for (i in routeGradeList.indices){
            routeStats.add(
                GradeRouteCompletionStatistics(
                    gradeName = routeGradeList[i].text, // adding the grade name here, may not be used but won't be horrible to have
                    completed = completeList[i],
                    incomplete = incompleteList[i],
                )
            )
        }

        return routeStats
    }

    // will return a list of the completed or incomplete list that we can use to graph the statistics
    fun getFormattedGradeList(completeStatus: RouteCompleteStatus, stats: List<GradeRouteCompletionStatistics>?): MutableList<Int> {

        val routeStats = mutableListOf<Int>()

        val routeGradeList: List<RouteGrade>? = when(routeFilter.value){
            RouteType.BOULDER -> getRouteGradeList(1) // gets boulder grades
            RouteType.TOP_ROPE, RouteType.LEAD_CLIMB -> getRouteGradeList(2) // gets top rope / lead climb grades
            null -> null
        }

        if (routeGradeList != null) {
            for(i in routeGradeList.indices){
                when(completeStatus){
                    RouteCompleteStatus.COMPLETED -> stats?.get(i)?.let { routeStats.add(it.completed) }
                    RouteCompleteStatus.INCOMPLETE -> stats?.get(i)?.let { routeStats.add(it.incomplete) }
                }
            }
        }

        return routeStats

    }

    fun getRouteGradeListBasedOnFilter(): List<RouteGrade>? {
        val routeGradeList: List<RouteGrade>? = when(routeFilter.value){
            RouteType.BOULDER -> getRouteGradeList(1) // gets boulder grades
            RouteType.TOP_ROPE, RouteType.LEAD_CLIMB -> getRouteGradeList(2) // gets top rope / lead climb grades
            null -> null
        }
        return routeGradeList
    }

    /* Big function that creates and returns the UserStatistics object for use in the statistics page */
    suspend fun getUserStatistics(): UserRouteStatistics {
            val userStats = UserRouteStatistics(
                totalCompletionStats =
                RouteCompletionStatistics(
                    completed = getTotalCompletedCount(),
                    attempted = getTotalAttemptedCount(),
                    totalClimbingTime = getTotalClimbingTime(),
                    shortestCompletedClimbingTime = getShortestClimbingTime(),
                    longestCompletedClimbingTime = getLongestClimbingTime()
                ),
                boulderCompletionStats =
                RouteCompletionStatistics(
                    completed = getCompletedCountByRouteType(RouteType.BOULDER.name),
                    attempted = getAttemptedCountByRouteType(RouteType.BOULDER.name),
                    totalClimbingTime = getClimbingTimeByRouteType(RouteType.BOULDER.name),
                    shortestCompletedClimbingTime = getShortestCompletedClimbingTimeByRouteType(RouteType.BOULDER.name),
                    longestCompletedClimbingTime = getLongestCompletedClimbingTimeByRouteType(RouteType.BOULDER.name),
                ),
                topRopeCompletionStats =
                RouteCompletionStatistics(
                    completed = getCompletedCountByRouteType(RouteType.TOP_ROPE.name),
                    attempted = getAttemptedCountByRouteType(RouteType.TOP_ROPE.name),
                    totalClimbingTime = getClimbingTimeByRouteType(RouteType.TOP_ROPE.name),
                    shortestCompletedClimbingTime = getShortestCompletedClimbingTimeByRouteType(RouteType.TOP_ROPE.name),
                    longestCompletedClimbingTime = getLongestCompletedClimbingTimeByRouteType(RouteType.TOP_ROPE.name),
                ),
                leadClimbCompletionStats =
                RouteCompletionStatistics(
                    completed = getCompletedCountByRouteType(RouteType.LEAD_CLIMB.name),
                    attempted = getAttemptedCountByRouteType(RouteType.LEAD_CLIMB.name),
                    totalClimbingTime = getClimbingTimeByRouteType(RouteType.LEAD_CLIMB.name),
                    shortestCompletedClimbingTime = getShortestCompletedClimbingTimeByRouteType(RouteType.LEAD_CLIMB.name),
                    longestCompletedClimbingTime = getLongestCompletedClimbingTimeByRouteType(RouteType.LEAD_CLIMB.name),
                ),

                /* below begins the backend calls for graph display for each particular route type */

                boulderGradeCompletionStats =
                    BoulderGradeCompletionStats(
                        boulderStats = assembleGradeCompletionStatisticsByRouteType(RouteType.BOULDER)
                    ),
                topRopeGradeCompletionStats =
                    TopRopeGradeCompletionStats(
                        topRopeStats = assembleGradeCompletionStatisticsByRouteType(RouteType.TOP_ROPE)
                    ),
                leadClimbGradeCompletionStats =
                    LeadClimbGradeCompletionStats(
                        leadClimbStats = assembleGradeCompletionStatisticsByRouteType(RouteType.LEAD_CLIMB)
                    )
            )
        return userStats
    }
}

