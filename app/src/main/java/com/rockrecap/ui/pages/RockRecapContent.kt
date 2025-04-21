package com.rockrecap.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rockrecap.ui.components.Header
import com.rockrecap.data.Route
import com.rockrecap.data.enums.RouteActiveStatus
import com.rockrecap.data.enums.RouteColor
import com.rockrecap.data.enums.RouteCompleteStatus
import com.rockrecap.data.enums.RouteGrade
import com.rockrecap.data.enums.RouteType
import com.rockrecap.data.navigation.NavigationRoutes
import com.rockrecap.data.RouteViewModel

@Composable
fun RockRecapContent(navController: NavHostController, viewModel: RouteViewModel){
    Column(modifier = Modifier
        .padding(start = 24.dp, end = 24.dp)){

        Header(navController, viewModel)

        NavHost(navController, startDestination = NavigationRoutes.ACTIVE_ROUTES_PAGE){
            composable(NavigationRoutes.ACTIVE_ROUTES_PAGE){
                ActiveRouteListPage(navController, viewModel)
            }
            composable(NavigationRoutes.INACTIVE_ROUTES_PAGE){
                InactiveRouteListPage(navController, viewModel)
            }
            composable(NavigationRoutes.ROUTE_DISPLAY_PAGE){
                RouteDisplayPage(navController, viewModel)
            }
            composable(NavigationRoutes.ADD_ROUTE_PAGE){
                AddRoutePage(navController, viewModel)
            }
            composable(NavigationRoutes.EDIT_ROUTE_PAGE){
                EditRoutePage(navController, viewModel)
            }
            composable(NavigationRoutes.STATISTICS_PAGE){
                StatisticsPage(navController, viewModel)
            }
        }
    }
}