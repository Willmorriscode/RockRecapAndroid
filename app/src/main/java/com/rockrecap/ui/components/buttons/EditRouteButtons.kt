package com.rockrecap.ui.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rockrecap.R
import com.rockrecap.data.enums.RouteActiveStatus
import com.rockrecap.data.enums.RouteCompleteStatus
import com.rockrecap.ui.theme.Black
import com.rockrecap.ui.theme.Secondary
import com.rockrecap.data.RouteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

// currently unused in the app
@Composable
fun EditRouteButtons(viewModel: RouteViewModel,
                     onActiveStatusChange: (String) -> Unit,
                     onCompletedStatusChange: (String) -> Unit)
{
    val selectedRoute = viewModel.selectedRoute.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom  = 24.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selectedRoute != null) {
                // RouteActiveStatus button
                if (selectedRoute.activeStatus == RouteActiveStatus.ACTIVE) {
                    // displays 'Deactivate Route' button
                    ChangeRouteActiveStatusButton(selectedRoute.activeStatus,
                        onClick = {
                            onActiveStatusChange(RouteActiveStatus.INACTIVE.name)
                        })
                } else {
                    // displays 'Activate Route' button
                    ChangeRouteActiveStatusButton(selectedRoute.activeStatus,
                        onClick = {
                            onActiveStatusChange(RouteActiveStatus.ACTIVE.name)
                        })
                }

                // RouteCompletedStatus button
                if (selectedRoute.completedStatus == RouteCompleteStatus.COMPLETED) {
                    // displays 'Mark Route Incomplete' button
                    ChangeRouteCompletedStatusButton(selectedRoute.completedStatus,
                        onClick = {
                            onCompletedStatusChange(RouteCompleteStatus.INCOMPLETE.name)
                        })
                } else {
                    // displays 'Mark Route Complete' button
                    ChangeRouteCompletedStatusButton(selectedRoute.completedStatus,
                        onClick = {
                            onCompletedStatusChange(RouteCompleteStatus.COMPLETED.name)
                        })
                }
            }
            }
    }
}

@Composable
fun ChangeRouteActiveStatusButton(routeStatus: RouteActiveStatus, onClick: () -> Unit) {
    val buttonText = if(routeStatus == RouteActiveStatus.ACTIVE){stringResource(id = R.string.deactivate_route)}else{
        stringResource(id = R.string.activate_route)}
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Secondary, // Use theme's primary color
            contentColor = Black   // Use theme's onPrimary color
        ),
        shape = RoundedCornerShape(22.dp),
        onClick = onClick
    ) {
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = buttonText
        )
    }
}

@Composable
fun ChangeRouteCompletedStatusButton(routeStatus: RouteCompleteStatus, onClick: () -> Unit) {
    val buttonText = if(routeStatus == RouteCompleteStatus.COMPLETED){
        stringResource(id = R.string.un_complete)}else{
        stringResource(id = R.string.complete)}
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Secondary, // Use theme's primary color
            contentColor = Black   // Use theme's onPrimary color
        ),
        shape = RoundedCornerShape(22.dp),
        onClick = onClick
    ) {
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = buttonText
        )
    }
}