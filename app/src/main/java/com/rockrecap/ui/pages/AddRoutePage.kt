package com.rockrecap.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.rockrecap.R
import com.rockrecap.ui.components.RouteForm
import com.rockrecap.ui.components.PageTitle
import com.rockrecap.data.RouteViewModel

@Composable
fun AddRoutePage(navController: NavHostController, viewModel: RouteViewModel){
    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .verticalScroll(scrollState)
    ){
        PageTitle(stringResource(id = R.string.add_new_route))
        RouteForm(viewModel, navController)
    }
}