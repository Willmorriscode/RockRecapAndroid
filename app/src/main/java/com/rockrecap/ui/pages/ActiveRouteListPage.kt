package com.rockrecap.ui.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.rockrecap.R
import com.rockrecap.ui.components.RouteListContent
import com.rockrecap.data.RouteViewModel

@Composable
fun ActiveRouteListPage(navController: NavHostController, viewModel: RouteViewModel){

    RouteListContent(navController, viewModel, stringResource(id = R.string.active_routes), viewActiveRoutes = true)
}