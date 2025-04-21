package com.rockrecap.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rockrecap.R
import com.rockrecap.ui.components.RouteForm
import com.rockrecap.ui.components.PageTitle
import com.rockrecap.ui.components.buttons.EditRouteButtons
import com.rockrecap.ui.components.buttons.BackButton
import com.rockrecap.data.navigation.NavigationRoutes
import com.rockrecap.data.RouteViewModel
import kotlinx.coroutines.launch

@Composable
fun EditRoutePage(navController: NavHostController, viewModel: RouteViewModel){
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier
        .verticalScroll(scrollState)
    ){
        BackButton(onBackSelected = { navController.popBackStack() })
        PageTitle(stringResource(id = R.string.edit_route))
        RouteForm(viewModel, navController, true)
        Row(
            modifier = Modifier
                .padding(vertical = 0.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
        }
    }
}

