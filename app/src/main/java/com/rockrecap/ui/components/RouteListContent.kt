package com.rockrecap.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rockrecap.ui.components.buttons.ViewInactiveRoutesButton
import com.rockrecap.data.navigation.NavigationRoutes
import com.rockrecap.data.RouteViewModel

@Composable
fun RouteListContent(
                     navController: NavHostController,
                     viewModel: RouteViewModel,
                     headerText: String,
                     viewActiveRoutes: Boolean,
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
            // if viewing active routes and no routes exist
            if(viewActiveRoutes && activeRouteUiState.routeList.isEmpty()){
                item{
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            modifier = Modifier,
                            text = "No Active routes found. Add more in the hamburger menu"
                        )
                    }
                }
            }
            // if viewing inactive routes and none exist
            if(!viewActiveRoutes && inactiveRouteUiState.routeList.isEmpty()){
                item{
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            modifier = Modifier,
                            text = "No Inactive routes found"
                        )
                    }
                }
            }

            if(viewActiveRoutes){ // displays active routes
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
                // displays inactive routes
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
                if (viewActiveRoutes) {
                    ViewInactiveRoutesButton(
                        onClickViewRoute = {
                                navController.navigate(NavigationRoutes.INACTIVE_ROUTES_PAGE)
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

