package com.rockrecap.ui.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rockrecap.R
import com.rockrecap.data.enums.RouteActiveStatus
import com.rockrecap.data.enums.RouteCompleteStatus
import com.rockrecap.ui.theme.RockSuccess
import com.rockrecap.ui.theme.White
import com.rockrecap.data.RouteViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun CompleteRouteBtn(viewModel: RouteViewModel, onClick: () -> Unit){

    var buttonText by rememberSaveable { mutableStateOf("") }
    var buttonDisabled by rememberSaveable { mutableStateOf(false) }

    val selectedRoute = viewModel.selectedRoute.collectAsState().value

    if (selectedRoute != null) {
        if(selectedRoute.completedStatus == RouteCompleteStatus.COMPLETED){
            buttonText  = stringResource(id = R.string.route_completed)
        } else{
            buttonText = stringResource(id = R.string.complete_route)
        }
    }

    if (selectedRoute != null) {
        buttonDisabled = !(selectedRoute.activeStatus == RouteActiveStatus.INACTIVE ||
                selectedRoute.completedStatus == RouteCompleteStatus.COMPLETED)
    }

    Row(
          modifier = Modifier
              .fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
      ){
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .size(68.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = RockSuccess,
                contentColor = White
            ),
            enabled = buttonDisabled,
            shape = RoundedCornerShape(18.dp),
        ) {
            Text(modifier = Modifier,
                style = MaterialTheme.typography.titleMedium,
                text = buttonText,
            )
        }
      }
}