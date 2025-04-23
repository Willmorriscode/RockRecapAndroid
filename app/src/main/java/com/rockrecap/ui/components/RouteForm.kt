package com.rockrecap.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rockrecap.R
import com.rockrecap.data.Route
import com.rockrecap.ui.components.formdropdowns.GradeDropDownMenu
import com.rockrecap.ui.components.formdropdowns.HoursDropDownMenu
import com.rockrecap.ui.components.formdropdowns.MinutesDropDownMenu
import com.rockrecap.ui.components.formdropdowns.RouteTypeDropDownMenu
import com.rockrecap.ui.theme.RockSuccess
import com.rockrecap.ui.theme.Tertiary
import com.rockrecap.ui.theme.White
import com.rockrecap.data.RouteViewModel
import com.rockrecap.data.SnackbarController
import com.rockrecap.data.SnackbarEvent
import com.rockrecap.data.enums.RouteColor
import com.rockrecap.data.enums.RouteCompleteStatus
import com.rockrecap.data.enums.RouteGrade
import com.rockrecap.data.enums.RouteType
import com.rockrecap.data.enums.getRouteColorList
import com.rockrecap.data.enums.getRouteGradeList
import com.rockrecap.data.navigation.NavigationRoutes
import com.rockrecap.ui.theme.Black
import com.rockrecap.ui.theme.RockOutline
import com.rockrecap.ui.theme.customTextFieldColors
import com.rockrecap.ui.theme.textFieldBackground
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun RouteForm(viewModel: RouteViewModel, navController: NavHostController, viewEditPage: Boolean = false) {
    val coroutineScope = rememberCoroutineScope()

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
                viewModel.routeNameValue?.let {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = it,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        singleLine = true,
                        colors = customTextFieldColors(),
                        placeholder = { Text("ie. Backwall Challenge") },
                        onValueChange = {
                            validateName(
                                it,
                                isError = { valid ->  viewModel.setRouteNameError(valid) },
                                errorMessage = { msg -> viewModel.setRouteNameErrorMessage(msg) }
                            )
                            viewModel.onRouteNameChange(it)
                        },
                        trailingIcon = {
                            if (viewModel.routeNameError) {
                                Icon(
                                    imageVector = Icons.Filled.Info,
                                    contentDescription = "Error", tint = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        isError = viewModel.routeNameError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                    )
                }
                if (viewModel.routeNameError) {
                    Text(
                        text = viewModel.routeNameErrorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            AdvancedRouteColorPicker(viewModel){ color ->
                viewModel.onRouteColorChange(color)
                validateRouteColor(
                    viewModel.routeColorValue,
                    isError = { valid -> viewModel.setRouteColorError(valid) },
                )
            }

            if(viewModel.routeTypeValue != ""){ // if a value is selected in route type
                viewModel.setGradeSelectionIsEnabled(true)
            }

            RouteTypeDropDownMenu(viewModel) { routeType ->
                viewModel.onRouteTypeChange(routeType)
                viewModel.onRouteGradeChange("") // changes the viewmodel
                validateRouteType(
                    viewModel.routeTypeValue,
                    isError = { valid -> viewModel.setRouteTypeError(valid) },
                    errorMessage = { } // idk maybe come back to this
                )
            }
            GradeDropDownMenu(viewModel){ grade ->
                viewModel.onRouteGradeChange(grade) // this will update whenever the selection changes, and can do other things with the grade there
                validateRouteGrade(
                    viewModel,
                    isError = { valid -> viewModel.setRouteGradeError(valid) },
                )
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

                        if(viewEditPage){
                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = customTextFieldColors(),
                                value = viewModel.routeHourValue,
                                onValueChange = {
                                    viewModel.onRouteHourChange(it)
                                                },
                                maxLines = 1,
                                textStyle = MaterialTheme.typography.bodyMedium
                            )
                        }
                        else{
                            HoursDropDownMenu(viewModel.routeHourValue) { hours ->
                                viewModel.onRouteHourChange(hours)
                            }
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
                        if(viewEditPage){ // putting a text field instead of a dropdown for more control over total time logged when editing a page
                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = customTextFieldColors(),
                                value = viewModel.routeMinuteValue,
                                onValueChange = {
                                    viewModel.onRouteMinuteChange(it)
                                                },
                                maxLines = 1,
                                textStyle = MaterialTheme.typography.bodyMedium
                            )
                        }
                        else{
                            MinutesDropDownMenu(viewModel.routeMinuteValue) { minutes ->
                                viewModel.onRouteMinuteChange(minutes)
                            }
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(id = R.string.summary),
                    style = MaterialTheme.typography.labelMedium
                )
                viewModel.routeSummaryValue.let {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        colors = customTextFieldColors(),
                        value = it,
                        onValueChange = {
                            validateRouteSummary(
                                it,
                                isError = { valid -> viewModel.setRouteSummaryError(valid) },
                                errorMessage = { msg -> viewModel.setRouteSummaryErrorMessage(msg) }
                            )
                            viewModel.onRouteSummaryChange(it)
                        },
                        placeholder = { Text(stringResource(id = R.string.summary_placeholder_text)) },
                        maxLines = 5,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        trailingIcon = {
                            if (viewModel.routeSummaryError) {
                                Icon(
                                    imageVector = Icons.Filled.Info,
                                    contentDescription = "Error", tint = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        isError = viewModel.routeSummaryError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }
                if (viewModel.routeSummaryError) {
                    Text(
                        text = viewModel.routeSummaryErrorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            // add route button
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                val buttonText = if(viewEditPage){ stringResource(id = R.string.update) } else{ stringResource(id = R.string.submit) }
                val scope = rememberCoroutineScope()
                val context = LocalContext.current
                Button(
                    onClick = {
                        if (viewModel.validateForm()){ // validation should occur here
                            if(viewEditPage){ // if editing an existing route
                                navController.popBackStack() // go back to the RDP you were at before
                                coroutineScope.launch {
                                        viewModel.selectedRoute.value?.let{ route ->
                                            route.copy(
                                                name = viewModel.routeNameValue,
                                                summary = viewModel.routeSummaryValue,
                                                grade = RouteGrade.entries.first { it.text == viewModel.routeGradeValue } ,
                                                type = RouteType.entries.first { it.text == viewModel.routeTypeValue },
                                                color = RouteColor.entries.first { it.text == viewModel.routeColorValue },
                                                timeLogged = viewModel.routeHourValue.toInt() * 60 + viewModel.routeMinuteValue.toInt(),
                                            )

                                            }
                                            .let {
                                            if (it != null) {
                                                viewModel.updateRoute(it)
                                            }
                                        }
                                }
                            }
                            else{
                                // if adding a new route
                                val route = formatNewRoute(
                                                viewModel.routeNameValue,
                                                viewModel.routeGradeValue,
                                                viewModel.routeTypeValue,
                                                viewModel.routeColorValue,
                                                viewModel.routeHourValue,
                                                viewModel.routeMinuteValue,
                                                viewModel.routeSummaryValue,
                                            )
                                navController.navigate(NavigationRoutes.ACTIVE_ROUTES_PAGE)
                                coroutineScope.launch {
                                    viewModel.submitNewRoute(route)
                                }
                            }
                            val routeName = viewModel.routeNameValue
                            scope.launch {
                                SnackbarController.sendEvent(
                                    event = SnackbarEvent(
                                        message = (
                                                if(viewEditPage){
                                                    context.getString(R.string.route_edited_successfully)
                                                }
                                                else{
                                                    "'${routeName}' route created"
                                                }
                                                )
                                    )
                                )
                            }
                        }
                        else{
                            // the form is not valid, send a toast
                            coroutineScope.launch {
                                SnackbarController.sendEvent(
                                    event = SnackbarEvent(
                                        message = context.getString(R.string.form_has_errors)
                                    )
                                )
                            }
                        }
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

fun validateRouteSummary(it: String, isError: (Boolean) -> Unit, errorMessage: (String) -> Unit) {
    if (it.isBlank()){
        isError(true)
        errorMessage("Please enter a summary")
    }
    else if(it.length >= 100){
        isError(true)
        errorMessage("Please enter a summary of 100 characters or less")
    }
    else {
        isError(false)
    }
}

fun validateName(it: String, isError: (Boolean) -> Unit, errorMessage: (String) -> Unit) {
    if (it.isBlank()){
        isError(true)
        errorMessage("Please enter a route name")
    }
    else if(it.length >= 20){
        isError(true)
        errorMessage("Please enter a name of 20 characters or less")
    }
    else {
        isError(false)
    }
}

fun validateRouteType(it: String, isError: (Boolean) -> Unit, errorMessage: (String) -> Unit){
    if (it.isBlank()){
        isError(true)
        errorMessage("Please select a route type")
    }
    else {
        isError(false)
    }
}

fun validateRouteGrade(viewModel: RouteViewModel, isError: (Boolean) -> Unit){
    if (viewModel.routeGradeValue.isBlank()){
        isError(true)
    }

    // this check is not happening in the viewmodel, only here. Keep this in mind
    val gradeObj = RouteGrade.entries.first { it.text == viewModel.routeGradeValue }
    val option = when(viewModel.routeTypeValue){
        "Boulder" -> 1
        "Lead Climb", "Top Rope" -> 2
        else -> 3
    }
    val currentRouteOptions = getRouteGradeList(option)

    if(gradeObj !in currentRouteOptions){
        isError(true)
    }

    else {
        isError(false)
    }
}

fun validateRouteColor(it: String, isError: (Boolean) -> Unit){
    if (it.isBlank()){
        isError(true)
    }
    else {
        isError(false)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AdvancedRouteColorPicker(
    viewModel: RouteViewModel,
    color: (String) -> Unit)
{
    val options = getRouteColorList().map { it }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)){
        Text(
            text = stringResource(id = R.string.route_color),
            style = MaterialTheme.typography.labelMedium
        )
        FlowRow(modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(
                color = textFieldBackground,
            )
            .padding(4.dp),
            horizontalArrangement = Arrangement.Center
        ){
            for(routeObj in options){
                Column (modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 4.dp)
                    .size(42.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Button(modifier = Modifier
                            .size(45.dp),
                        onClick = {
                            color(routeObj.text)
                                  },
                        shape = CircleShape,
                        border = BorderStroke(
                            color = RockOutline,
                            width = if(routeObj.text == viewModel.routeColorValue){ // will make the border bigger to indicate you have selected a color
                                4.dp
                            }
                            else{
                                1.dp
                            },
                        ),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = routeObj.color, // Use theme's primary color
                            contentColor = Black   // Use theme's onPrimary color
                        ),

                    ){

                    }
                }
            }
        }
        if (viewModel.routeColorError) {
            Text(
                text = "Please select a route color",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
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

