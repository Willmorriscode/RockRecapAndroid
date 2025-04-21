package com.rockrecap.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.room.util.copy
import com.rockrecap.R
import com.rockrecap.data.Route
import com.rockrecap.ui.components.formdropdowns.ColorDropDownMenu
import com.rockrecap.ui.components.formdropdowns.GradeDropDownMenu
import com.rockrecap.ui.components.formdropdowns.HoursDropDownMenu
import com.rockrecap.ui.components.formdropdowns.MinutesDropDownMenu
import com.rockrecap.ui.components.formdropdowns.RouteTypeDropDownMenu
import com.rockrecap.ui.theme.RockSuccess
import com.rockrecap.ui.theme.Tertiary
import com.rockrecap.ui.theme.White
import com.rockrecap.data.RouteViewModel
import com.rockrecap.data.enums.RouteColor
import com.rockrecap.data.enums.RouteGrade
import com.rockrecap.data.enums.RouteType
import com.rockrecap.data.navigation.NavigationRoutes
import com.rockrecap.ui.theme.RockGray
import com.rockrecap.ui.theme.RockGray50
import com.rockrecap.ui.theme.Secondary
import com.rockrecap.ui.theme.customTextFieldColors
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun RouteForm(viewModel: RouteViewModel, navController: NavHostController, viewEditPage: Boolean = false) {
    val coroutineScope = rememberCoroutineScope()

    val routeState = viewModel.selectedRoute.collectAsState().value

    // all of these will default to empty string when the page isn't in edit, and then default to the existing route information when in edit
    var routeNameValue by remember { mutableStateOf(
        if (viewEditPage && routeState != null) routeState.name else ""
    ) }
    var routeGradeValue by remember { mutableStateOf(
        if (viewEditPage && routeState != null) routeState.grade.text else ""
    ) }
    var routeTypeValue by remember { mutableStateOf(
        if (viewEditPage && routeState != null) routeState.type.text else ""
    ) }
    var routeColorValue by remember { mutableStateOf(
        if (viewEditPage && routeState != null) routeState.color.text else ""
    ) }
    var routeMinuteValue by remember { mutableStateOf(
        if (viewEditPage && routeState != null) (routeState.timeLogged % 60).toString() else "0"
    ) }
    var routeHourValue by remember { mutableStateOf(
        if (viewEditPage && routeState != null) (routeState.timeLogged / 60).toString() else "0"
    ) }
    var routeSummaryValue by remember { mutableStateOf(
        if (viewEditPage && routeState != null) routeState.summary else ""
    ) }

    var routeNameError by rememberSaveable { mutableStateOf(false) }
    var routeGradeError by rememberSaveable { mutableStateOf(false) }
    var routeTypeError by rememberSaveable { mutableStateOf(false) }
    var routeColorError by rememberSaveable { mutableStateOf(false) }
    var routeTimeError by rememberSaveable { mutableStateOf(false) }
    var routeSummaryError by rememberSaveable { mutableStateOf(false) }

    var gradeSelectionIsEnabled by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier // column holding the whole form
            .fillMaxWidth()
            .background(
                color = Tertiary, // Your saved Color object
                shape = RoundedCornerShape(22.dp) // Adjust the corner radius as needed
            )
            .padding(24.dp, 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.width(320.dp), // column which determines the width of the elements in the form
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(id = R.string.route_name),
                    style = MaterialTheme.typography.labelMedium
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = routeNameValue,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    singleLine = true,
                    colors = customTextFieldColors(),
                    placeholder = { Text("ie. Backwall Challenge") },
                    onValueChange = {
                        if (it.isNotBlank()) { // no errors occur here
                            routeNameError = false
                        } else {
                            routeNameError = true
                        }
                        routeNameValue = it
                    },
                    trailingIcon = {
                        if (routeNameError) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Error", tint = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    isError = routeNameError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                )
                if (routeNameError) {
                    Text(
                        text = "Must give the route a name",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            ColorDropDownMenu(routeColorValue){ color ->
                routeColorValue = color
            }

            if(routeTypeValue != ""){ // if a value is selected in route type
                gradeSelectionIsEnabled = true
            }

            RouteTypeDropDownMenu(routeTypeValue) { routeType ->
                routeTypeValue = routeType
            }

            GradeDropDownMenu(routeGradeValue, gradeSelectionIsEnabled, routeTypeValue){ grade ->
                routeGradeValue = grade // this will update whenever the selection changes, and can do other things with the grade there
            }

            Column(modifier = Modifier
                .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically){
                    Column(
                        modifier = Modifier
                            .width(120.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        Text(
                            text = stringResource(id = R.string.hours),
                            style = MaterialTheme.typography.labelMedium
                        )
                        HoursDropDownMenu(routeHourValue) { hours ->
                            routeHourValue = hours
                        }
                    }
                    Column(
                        modifier = Modifier
                            .width(120.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        Text(
                            text = stringResource(id = R.string.minutes),
                            style = MaterialTheme.typography.labelMedium
                        )
                        MinutesDropDownMenu(routeMinuteValue) { minutes ->
                            routeMinuteValue = minutes
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(id = R.string.summary),
                    style = MaterialTheme.typography.labelMedium
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    colors = customTextFieldColors(),
                    value = routeSummaryValue,
                    onValueChange = { routeSummaryValue = it },
                    placeholder = { Text(stringResource(id = R.string.summary_placeholder_text)) },
                    maxLines = 5,
                    textStyle = MaterialTheme.typography.bodyMedium
                )
            }
            // add route button
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                val buttonText = if(viewEditPage){ stringResource(id = R.string.update) } else{ stringResource(id = R.string.submit) }
                Button(
                    onClick = {
                        if (true){ // validation should occur here
                            if(viewEditPage){
                                navController.popBackStack() // go back to the RDP you were at before
                                coroutineScope.launch {
                                        viewModel.selectedRoute.value?.let{ route ->
                                            viewModel.updateRoute(route.copy(
                                                name = routeNameValue,
                                                summary = routeSummaryValue,
                                                grade = RouteGrade.entries.first { it.text == routeGradeValue } ,
                                                type = RouteType.entries.first { it.text == routeTypeValue },
                                                color = RouteColor.entries.first { it.text == routeColorValue },
                                                timeLogged = routeHourValue.toInt() * 60 + routeMinuteValue.toInt(),
                                            ))
                                        }
                                }
                            }
                            else{
                                val route = formatNewRoute(
                                    routeNameValue,
                                    routeGradeValue,
                                    routeTypeValue,
                                    routeColorValue,
                                    routeHourValue,
                                    routeMinuteValue,
                                    routeSummaryValue,
                                )

                                navController.navigate(NavigationRoutes.ACTIVE_ROUTES_PAGE)
                                coroutineScope.launch {
                                    viewModel.submitNewRoute(route)
                                }
                            }
                        }

                        Log.d("Add Route Button Pressed", "$routeNameValue, $routeColorValue, $routeTypeValue, $routeGradeValue, $routeMinuteValue, $routeHourValue, $routeSummaryValue")
                        /* TODO, should add the route to the routes list, pop up a toast, then go to active routes */
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(68.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RockSuccess,
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(18.dp),
                ) {
                    Text(modifier = Modifier,
                        text = buttonText,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

// updates an existing route based on the route ID
fun formatEditedRoute(
    originalRoute: StateFlow<Route?>,
    routeName: String,
    routeGrade: String,
    routeType: String,
    routeColor: String,
    routeHour: String,
    routeMinute: String,
    routeSummary: String) : Route? {
    return originalRoute.value?.let { it ->
        Route(
        routeId = it.routeId,
        name = routeName,
        summary = routeSummary,
        type = RouteType.entries.first { it.text == routeType },
        color = RouteColor.entries.first { it.text == routeColor },
        grade = RouteGrade.entries.first { it.text == routeGrade },
        timeLogged = routeHour.toInt() * 60 + routeMinute.toInt(),
        activeStatus = it.activeStatus,
        completedStatus = it.completedStatus,
        startDate = it.startDate,
        routeBelongsTo = it.routeBelongsTo,
        routeTemplateCreatedBy = it.routeTemplateCreatedBy
        )
    }
}

fun formatNewRoute(
    routeName: String,
    routeGrade: String,
    routeType: String,
    routeColor: String,
    routeHour: String,
    routeMinute: String,
    routeSummary: String
): Route {
    return Route(
        name = routeName,
        summary = routeSummary,
        grade = RouteGrade.entries.first { it.text == routeGrade } ,
        type = RouteType.entries.first { it.text == routeType },
        color = RouteColor.entries.first { it.text == routeColor },
        timeLogged = routeHour.toInt() * 60 + routeMinute.toInt(),
        startDate = Calendar.getInstance().timeInMillis
    )
}

