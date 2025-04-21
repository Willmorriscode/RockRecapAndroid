package com.rockrecap.data

import com.rockrecap.data.enums.RouteGrade
import com.rockrecap.data.enums.getRouteGradeList

data class UserRouteStatistics(
    // holds all the information about amount of routes completed, total routes, etc.
    val totalCompletionStats: RouteCompletionStatistics = RouteCompletionStatistics(),
    val boulderCompletionStats: RouteCompletionStatistics = RouteCompletionStatistics(),
    val topRopeCompletionStats: RouteCompletionStatistics = RouteCompletionStatistics(),
    val leadClimbCompletionStats: RouteCompletionStatistics = RouteCompletionStatistics(),

    val boulderGradeCompletionStats: BoulderGradeCompletionStats = BoulderGradeCompletionStats(),
    val topRopeGradeCompletionStats: TopRopeGradeCompletionStats = TopRopeGradeCompletionStats(),
    val leadClimbGradeCompletionStats: LeadClimbGradeCompletionStats = LeadClimbGradeCompletionStats(),
)

// select where route == Completed
data class RouteCompletionStatistics(
    val completed: Int = 0, // select count where route == Completed
    val attempted: Int = 0, // select count where route == Incomplete
    val completionRate: Float =
        /* avoid divide by 0 NaN issue */
        if(attempted == 0) { 0f } else{ (completed.toFloat() / attempted.toFloat()) },
    val totalClimbingTime: Int = 0,  // select sum(timeLogged)
    val shortestCompletedClimbingTime: Int = 0, // select min(timeLogged)
    val longestCompletedClimbingTime: Int = 0 // select max(timeLogged)
)

data class GradeRouteCompletionStatistics(
    val gradeName: String = "none",
    val completed: Int = 0,
    val incomplete: Int = 0,
    val totalAttempted: Int = completed + incomplete,
    val completionRate: Float =
    /* avoid divide by 0 NaN issue */
        if (totalAttempted == 0){ 0f } else{ completed.toFloat()  / totalAttempted.toFloat() }
)

// figure out what makes sense to store for this graph
data class BoulderGradeCompletionStats(
    val boulderStats: List<GradeRouteCompletionStatistics> = listOf() // Initialize each grade with default stats
)

data class TopRopeGradeCompletionStats(
    val topRopeStats: List<GradeRouteCompletionStatistics> = listOf() // Initialize each grade with default stats
)

data class LeadClimbGradeCompletionStats(
    val leadClimbStats: List<GradeRouteCompletionStatistics> = listOf() // Initialize each grade with default stats
)


