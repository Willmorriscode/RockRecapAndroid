package com.rockrecap.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rockrecap.data.Route
import com.rockrecap.ui.components.buttons.ViewRoutesButton
import com.rockrecap.data.navigation.NavigationRoutes
import com.rockrecap.data.RouteViewModel

@Composable
fun RouteListContent(
                     navController: NavHostController,
                     viewModel: RouteViewModel,
                     headerText: String,
                     viewActiveRoutes: Boolean = false,
){
    val activeRouteUiState by viewModel.activeRoutesUiState.collectAsState()
    val inactiveRouteUiState by viewModel.inactiveRoutesUiState.collectAsState()

    Column (modifier = Modifier,
    ){
        LazyColumn (modifier = Modifier
            .fillMaxWidth(),
        ){
            item{
                PageTitle(headerText)
            }

            if(!viewActiveRoutes){
                // creating and passing variables to each routeListCard
                itemsIndexed(activeRouteUiState.routeList) { index, route ->
                    RouteListCard(
                        route,
                        onCardClick = {
                            viewModel.setSelectedRoute(route)
                            navController.navigate(NavigationRoutes.ROUTE_DISPLAY_PAGE)
                        }
                    )
                }
            }

            else{
                // creating and passing variables to each routeListCard
                itemsIndexed(inactiveRouteUiState.routeList) { index, route ->
                    RouteListCard(
                        route,
                        onCardClick = {
                            viewModel.setSelectedRoute(route)
                            navController.navigate(NavigationRoutes.ROUTE_DISPLAY_PAGE)
                        }
                    )
                }

            }

            item{
                if (!viewActiveRoutes) {
                    ViewRoutesButton(viewActiveRoutes,
                        onClickViewRoute = {
                            if(viewActiveRoutes){
                                navController.navigate(NavigationRoutes.ACTIVE_ROUTES_PAGE)
                            }
                            else{
                                navController.navigate(NavigationRoutes.INACTIVE_ROUTES_PAGE)
                            }
                        }
                    )
                }
            }
            item{
                Spacer(modifier = Modifier
                    .height(128.dp))
            }
        }
    }
}

