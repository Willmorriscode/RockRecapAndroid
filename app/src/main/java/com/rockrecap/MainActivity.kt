package com.rockrecap

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.rockrecap.ui.pages.RockRecapContent
import com.rockrecap.ui.theme.RockRecapTheme
import com.rockrecap.ui.theme.White
import com.rockrecap.ui.AppViewModelProvider

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter") // this allows us to not have to use the inner padding parameter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            RockRecapTheme {
                Scaffold( containerColor = White,
                    modifier = Modifier
                    .fillMaxSize()
                ) {
//                    classTestFunction() // will test the functionality of the object used to store statistical data in the users stats page
                    RockRecapContent(navController, viewModel(factory = AppViewModelProvider.Factory))
                }
            }
        }
    }
}

// test function that chat GPT wrote that is going to be extremely important to making queries to the backend to create data to use on the frontend
//fun classTestFunction(){
//    // 1. Create instances with default values
//    var totalStats = RouteCompletionStatistics()
//
//    var boulderStats = RouteCompletionStatistics()
//    var boulderGradeStats = BoulderGradeCompletionStats()
//    var topRopeStats = RouteCompletionStatistics()
//    var topRopeGradeStats = TopRopeGradeCompletionStats()
//    var leadClimbStats = RouteCompletionStatistics()
//    var leadClimbGradeStats = LeadClimbGradeCompletionStats()
//
//    // 2. Modify some values using .copy()
//    totalStats = totalStats.copy(
//        completed = 120,
//        attempted = 150,
//        completionRate = 120f / 150f,
//        totalClimbingTime = 3200,
//        shortestClimbingTime = 5,
//        longestClimbingTime = 45
//    )
//
//    boulderStats = boulderStats.copy(
//        completed = 60,
//        attempted = 80,
//        completionRate = 60f / 80f,
//        totalClimbingTime = 1500,
//        shortestClimbingTime = 3,
//        longestClimbingTime = 30
//    )
//
//    topRopeStats = topRopeStats.copy(
//        completed = 40,
//        attempted = 50,
//        completionRate = 40f / 50f,
//        totalClimbingTime = 2000,
//        shortestClimbingTime = 6,
//        longestClimbingTime = 50
//    )
//
//    leadClimbStats = leadClimbStats.copy(
//        completed = 20,
//        attempted = 30,
//        completionRate = 20f / 30f,
//        totalClimbingTime = 1800,
//        shortestClimbingTime = 10,
//        longestClimbingTime = 55
//    )
//
//    // Modify Boulder Grade Completion Stats (Set completed for V5-V7)
//    val updatedBoulderGrades = boulderGradeStats.counts.toMutableMap()
//    updatedBoulderGrades[RouteGrade.V5_V7] = GenericRouteCompletionStats(completed = 10, total = 12)
//    boulderGradeStats = boulderGradeStats.copy(counts = updatedBoulderGrades)
//
//    // Modify Top Rope Grade Completion Stats (Set completed for 5.10)
//    val updatedTopRopeGrades = topRopeGradeStats.counts.toMutableMap()
//    updatedTopRopeGrades[RouteGrade.R5_10] = GenericRouteCompletionStats(completed = 5, total = 7)
//    topRopeGradeStats = topRopeGradeStats.copy(counts = updatedTopRopeGrades)
//
//    // Modify Lead Climb Grade Completion Stats (Set completed for 5.12)
//    val updatedLeadClimbGrades = leadClimbGradeStats.counts.toMutableMap()
//    updatedLeadClimbGrades[RouteGrade.R5_12] = GenericRouteCompletionStats(completed = 3, total = 5)
//    leadClimbGradeStats = leadClimbGradeStats.copy(counts = updatedLeadClimbGrades)
//
//    // 3. Assign to UserRouteStatistics
//    val userStats = UserRouteStatistics(
//        totalCompletionStats = totalStats,
//        boulderCompletionStats = boulderStats,
//        topRopeCompletionStats = topRopeStats,
//        leadClimbCompletionStats = leadClimbStats,
//        boulderGradeCompletionStats = boulderGradeStats,
//        topRopeGradeCompletionStats = topRopeGradeStats,
//        leadClimbGradeCompletionStats = leadClimbGradeStats
//    )
//
//    // 4. Accessing Values
//    println("Total Routes Completed: ${userStats.totalCompletionStats.completed}")
//    println("Boulder Completion Rate: ${userStats.boulderCompletionStats.completionRate * 100}%")
//    println("Top Rope Longest Climb Time: ${userStats.topRopeCompletionStats.longestClimbingTime} min")
//    println("Lead Climb Attempted: ${userStats.leadClimbCompletionStats.attempted}")
//    println("Boulder V5-V7 Completed: ${userStats.boulderGradeCompletionStats.counts[RouteGrade.V5_V7]?.completed}")
//    println("Top Rope 5.10 Total: ${userStats.topRopeGradeCompletionStats.counts[RouteGrade.R5_10]?.total}")
//    println("Lead Climb 5.12 Completion Rate: ${userStats.leadClimbGradeCompletionStats.counts[RouteGrade.R5_12]?.completed}/${userStats.leadClimbGradeCompletionStats.counts[RouteGrade.R5_12]?.total}")
//}
