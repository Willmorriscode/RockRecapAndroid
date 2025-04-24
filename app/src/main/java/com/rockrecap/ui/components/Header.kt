package com.rockrecap.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rockrecap.R
import com.rockrecap.data.enums.PageNames
import com.rockrecap.data.enums.getPageNamesList
import com.rockrecap.data.navigation.NavigationRoutes
import com.rockrecap.data.RouteViewModel
import com.rockrecap.ui.theme.Tertiary

@Composable
fun Header(navController: NavHostController, viewModel: RouteViewModel){
    Box(modifier = Modifier
        .height(24.dp)
    )
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(64.dp)
        .padding(vertical = 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier
                .padding()
                .size(134.dp)
                .clickable {
                    navController.navigate(NavigationRoutes.ACTIVE_ROUTES_PAGE)
                    // used to reset the view in the statistics page if navigated through the hamburger menu
                    viewModel.updateRouteFilter(null)
                    viewModel.resetRouteFormPage()
                    viewModel.updateDisplayConfetti(false)
                           },
            painter = painterResource(id = R.drawable.rockrecap_logo),
            contentDescription = "Rock Recap Logo",
        )

        HamburgerDropDownMenu (){ selectionOption ->
//                viewModel.unsetSelectedRoute() // issue here is on the RDP we literally see this being changed in real time

            viewModel.resetRouteFormPage()
            when(selectionOption){
                PageNames.ACTIVE_ROUTES.text -> navController.navigate(NavigationRoutes.ACTIVE_ROUTES_PAGE)
                PageNames.INACTIVE_ROUTES.text -> navController.navigate(NavigationRoutes.INACTIVE_ROUTES_PAGE)
                PageNames.ADD_NEW_ROUTE.text -> navController.navigate(NavigationRoutes.ADD_ROUTE_PAGE)
                PageNames.YOUR_STATISTICS.text -> navController.navigate(NavigationRoutes.STATISTICS_PAGE)
            }

            // used to reset the view in the statistics page if navigated through the hamburger menu
            viewModel.updateRouteFilter(null)
            viewModel.updateDisplayConfetti(false)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HamburgerDropDownMenu(onHamburgerMenuClick: (String) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        val options = getPageNamesList().map { it.text }
        var expanded by remember { mutableStateOf(false) }
        var routeTypeValue by remember { mutableStateOf("") }

        val burgerIconPath = Icons.Filled.Menu
        val downIconPath = Icons.Filled.ArrowDropDown

        ExposedDropdownMenuBox(modifier = Modifier,
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }) {

            Image(
                modifier = Modifier
                    .padding()
                    .size(24.dp)
                    .menuAnchor(),
                imageVector  = if (expanded) downIconPath else burgerIconPath,
                contentDescription = "Hamburger Button",
            )
            ExposedDropdownMenu(
                modifier = Modifier
                    .width(170.dp)
                    .background(Tertiary),
                expanded = expanded, onDismissRequest = { expanded = false }
            ){
                options.forEach{ selectionOption ->
                    DropdownMenuItem(text = {
                        Text(
                            text = selectionOption,
//                            style = MaterialTheme.typography.labelLarge // changing text styling here would affect the dropdown
                        )},
                        onClick = {
                            onHamburgerMenuClick(selectionOption)
                            routeTypeValue = selectionOption
                            expanded = false
                        })
                }
            }
        }
    }
}
