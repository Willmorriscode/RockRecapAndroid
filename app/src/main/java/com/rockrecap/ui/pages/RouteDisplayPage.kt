package com.rockrecap.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.rockrecap.R
import com.rockrecap.data.Route
import com.rockrecap.ui.components.PageTitle
import com.rockrecap.ui.components.buttons.BackButton
import com.rockrecap.ui.components.buttons.CompleteRouteBtn
import com.rockrecap.ui.components.buttons.AddTimeButton
import com.rockrecap.ui.components.chips.RouteActiveStatusChip
import com.rockrecap.ui.components.chips.RouteColorChip
import com.rockrecap.ui.components.chips.RouteCompleteStatusChip
import com.rockrecap.ui.components.modals.AddTimeModal
import com.rockrecap.data.enums.RouteActiveStatus
import com.rockrecap.data.enums.RouteDisplayPageMenuOptions
import com.rockrecap.data.enums.getRouteDisplayPageMenuOptionsList
import com.rockrecap.data.navigation.NavigationRoutes
import com.rockrecap.ui.theme.Black
import com.rockrecap.ui.theme.Secondary
import com.rockrecap.ui.theme.Tertiary
import com.rockrecap.ui.theme.White
import com.rockrecap.utilities.formatRouteDate
import com.rockrecap.utilities.formatRouteTime
import com.rockrecap.data.RouteViewModel
import com.rockrecap.data.SnackbarController
import com.rockrecap.data.SnackbarEvent
import com.rockrecap.data.enums.RouteCompleteStatus
import kotlinx.coroutines.launch

@Composable
fun RouteDisplayPage(navController: NavHostController, viewModel: RouteViewModel){
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var showAddTimeModal by rememberSaveable {
        mutableStateOf(false)
    }

    var showDeleteRouteModal by rememberSaveable {
        mutableStateOf(false)
    }

    val selectedRoute = viewModel.selectedRoute.collectAsState().value

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
    ){
        if(selectedRoute != null){
            BackButton(onBackSelected = {
                // page navigation
                navController.popBackStack()
            })
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                PageTitle(selectedRoute.name)
                RouteDisplayPageDropDownMenu(selectedRoute){ onMenuOptionSelected ->
                    when(onMenuOptionSelected){
                        RouteDisplayPageMenuOptions.EDIT_ROUTE.text -> {
                                viewModel.enterEditRoutePage()
                                navController.navigate(NavigationRoutes.EDIT_ROUTE_PAGE)
                        }
                        RouteDisplayPageMenuOptions.DELETE_ROUTE.text -> showDeleteRouteModal = true
                        RouteDisplayPageMenuOptions.DEACTIVATE_ROUTE.text -> {
                            coroutineScope.launch() {
                                viewModel.selectedRoute.value?.let { viewModel.updateActiveStatus(it, RouteActiveStatus.INACTIVE.name) }
                                SnackbarController.sendEvent(
                                    event = SnackbarEvent(
                                        message = context.getString(R.string.route_active_status_changed_to) + RouteActiveStatus.INACTIVE.text
                                    )
                                )
                                }
                            }
                        RouteDisplayPageMenuOptions.ACTIVATE_ROUTE.text ->{
                            coroutineScope.launch() {
                                viewModel.selectedRoute.value?.let { viewModel.updateActiveStatus(it, RouteActiveStatus.ACTIVE.name)}
                                SnackbarController.sendEvent(
                                    event = SnackbarEvent(
                                        message = context.getString(R.string.route_active_status_changed_to) + RouteActiveStatus.ACTIVE.text
                                    )
                                )
                            }
                        }
                        // unused
                        RouteDisplayPageMenuOptions.MARK_COMPLETE.text -> {
                            coroutineScope.launch(){
                                viewModel.selectedRoute.value?.let { viewModel.updateCompletedStatus(it, RouteCompleteStatus.COMPLETED.name)}
                            }
                        }
                        RouteDisplayPageMenuOptions.MARK_INCOMPLETE.text -> {
                            coroutineScope.launch(){
                                viewModel.selectedRoute.value?.let { viewModel.updateCompletedStatus(it, RouteCompleteStatus.INCOMPLETE.name) }
                                SnackbarController.sendEvent(
                                    event = SnackbarEvent(
                                        message = context.getString(R.string.route_completion_status_changed_to) + RouteCompleteStatus.INCOMPLETE.text
                                    )
                                )
                            }
                        }
                    }
                }
            }

            if(showDeleteRouteModal){
                DeleteRouteModal(
                    onDismissRequest = { showDeleteRouteModal = false },
                    deleteButtonClick = {
                        // save the status before deleting to be able to navigate!
                        val pageToView = viewModel.selectedRoute.value?.activeStatus
                        coroutineScope.launch(){
                            val routeName = viewModel.selectedRoute.value?.name
                            viewModel.selectedRoute.value?.let { viewModel.deleteRoute(it) }
                            SnackbarController.sendEvent(
                                event = SnackbarEvent(
                                    message = "'${routeName}' route deleted"
                                )
                            )
                        }
                        if(pageToView == RouteActiveStatus.ACTIVE){
                            navController.navigate(NavigationRoutes.ACTIVE_ROUTES_PAGE)
                        }
                        else{
                            navController.navigate(NavigationRoutes.INACTIVE_ROUTES_PAGE)
                        }
                    }
                )
            }

            Row(modifier = Modifier
                .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ){
                RouteActiveStatusChip(selectedRoute.activeStatus)
                RouteCompleteStatusChip(selectedRoute.completedStatus)
                RouteColorChip(selectedRoute.color)
            }
            Row(modifier = Modifier// holds the information above the card
                .fillMaxWidth()
                .padding(vertical = 48.dp),
                horizontalArrangement = Arrangement.spacedBy(120.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Column(modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                    ){
                    Text(modifier = Modifier,
                        style = MaterialTheme.typography.labelMedium,
                        text = stringResource(id = R.string.route_type))
                    Text(modifier = Modifier,
                        text = selectedRoute.type.text,
                        style = MaterialTheme.typography.labelLarge,
                        )
                }
                Column(modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(10.dp)){
                    Text(
                        text = stringResource(R.string.difficulty_rating),
                        style = MaterialTheme.typography.labelMedium)
                    Text(
                        text = selectedRoute.grade.text,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
            Column(modifier = Modifier, // container holds button and card with route info
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Card(modifier = Modifier
                    .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Tertiary
                    ),
                    shape = RoundedCornerShape(18.dp)
                ){
                    Column(modifier = Modifier
                        .padding(vertical = 36.dp, horizontal = 28.dp)
                        .wrapContentSize(),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ){
                        Row(modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp),){
                                Text(
                                    stringResource(id = R.string.time_spent),
                                    style = MaterialTheme.typography.labelMedium)
                                Text(formatRouteTime(selectedRoute.timeLogged),
                                    style = MaterialTheme.typography.labelLarge)
                            }

                            AddTimeButton(
                                onClick = { showAddTimeModal = true},
                                if(selectedRoute.completedStatus == RouteCompleteStatus.COMPLETED
                                    || selectedRoute.activeStatus == RouteActiveStatus.INACTIVE){ false } else { true })
                            if (showAddTimeModal){
                                AddTimeModal(
                                    onDismissRequest = { showAddTimeModal = false },
                                    submitButtonClick = { newTime ->
                                        if (newTime != 0){
                                            coroutineScope.launch(){
                                                viewModel.selectedRoute.value?.let { viewModel.updateRouteTime(it, newTime) }
                                                SnackbarController.sendEvent(
                                                    event = SnackbarEvent(
                                                        message = "${formatRouteTime(newTime)} added to total route time"
                                                    )
                                                )
                                            }
                                        }
                                    },
                                )
                            }
                        }
                        Column(modifier = Modifier
                            .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)){
                            Text(
                                text = stringResource(id = R.string.summary),
                                style = MaterialTheme.typography.labelMedium)
                            Text(
                                text = selectedRoute.summary,
                                style = MaterialTheme.typography.labelLarge)
                        }
                        Column(modifier = Modifier
                            .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)){
                            Text(
                                text = stringResource(id = R.string.date_started),
                                style = MaterialTheme.typography.labelMedium)
                            Text(
                                text = formatRouteDate(selectedRoute.startDate,1),
                                style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
                CompleteRouteBtn(
                    viewModel,
                    onClick = {
                        coroutineScope.launch(){
                            viewModel.selectedRoute.value?.let { viewModel.updateCompletedStatus(it, RouteCompleteStatus.COMPLETED.name) }
                            SnackbarController.sendEvent(
                                event = SnackbarEvent(
                                    message = "${context.getString(R.string.completed_route_message)} '${viewModel.selectedRoute.value?.name}'"
                                )
                            )
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteDisplayPageDropDownMenu(selectedRoute: Route, onMenuOptionSelected: (String) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        val options = getRouteDisplayPageMenuOptionsList(selectedRoute.activeStatus, selectedRoute.completedStatus).map { it.text }
        var expanded by remember { mutableStateOf(false) }
        var routeTypeValue by remember { mutableStateOf("") }

        val threeDotsIcon = Icons.Filled.MoreVert
        val downArrowIcon = Icons.Filled.ArrowDropDown

        ExposedDropdownMenuBox(modifier = Modifier,
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }) {

            Image(
                modifier = Modifier
                    .size(28.dp)
                    .menuAnchor(),
                imageVector = if (expanded) downArrowIcon else threeDotsIcon,
                contentDescription = "Three Dots",
            )
            ExposedDropdownMenu(
                modifier = Modifier
                    .width(180.dp)
                    .background(Tertiary),
                expanded = expanded, onDismissRequest = { expanded = false }
            ){
                options.forEach{ selectionOption ->
                    DropdownMenuItem(text = { Text(text = selectionOption) },
                        onClick = {
                            onMenuOptionSelected(selectionOption)
                            routeTypeValue = selectionOption
                            expanded = false
                        })
                }
            }
        }
    }
}

@Composable
fun DeleteRouteModal(
    onDismissRequest: () -> Unit,
    deleteButtonClick: () -> Unit
){
    Dialog( onDismissRequest = onDismissRequest ) {
        Card(
            modifier = Modifier,
//                .width(500.dp),
            colors = CardDefaults.cardColors(
                containerColor = Tertiary
            ),){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 36.dp),
                verticalArrangement = Arrangement.spacedBy(36.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = stringResource(id = R.string.confirm_route_deletion),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(0.dp)
                )
                Box(modifier = Modifier
                    .width(220.dp)){
                    Text(
                        text = stringResource(R.string.route_deletion_confirmation_message),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Row(modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                    ){
                    Button(
                        onClick = {
                            onDismissRequest()
                        },
                        modifier = Modifier
                            .padding(top = 0.dp)
                            .width(120.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Secondary,
                            contentColor = Black
                        ),
                        shape = RoundedCornerShape(24.dp),
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Button(
                        onClick = {
                            onDismissRequest()
                            deleteButtonClick()
                        },
                        modifier = Modifier
                            .padding(top = 0.dp)
                            .width(120.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = White
                        ),
                        shape = RoundedCornerShape(24.dp),
                    ) {
                        Text(
                            text = stringResource(id = R.string.delete),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
