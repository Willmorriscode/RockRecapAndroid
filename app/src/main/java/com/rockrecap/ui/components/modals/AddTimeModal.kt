package com.rockrecap.ui.components.modals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.rockrecap.R
import com.rockrecap.ui.components.formdropdowns.HoursDropDownMenu
import com.rockrecap.ui.components.formdropdowns.MinutesDropDownMenu
import com.rockrecap.ui.theme.Black
import com.rockrecap.ui.theme.Secondary
import com.rockrecap.ui.theme.Tertiary

@Composable
fun AddTimeModal(
    onDismissRequest: () -> Unit,
    submitButtonClick: (Int) -> Unit
){
    var minutesValue by remember { mutableStateOf(0) }
    var hoursValue by remember { mutableStateOf(0) }

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
                    text = stringResource(id = R.string.add_route_time),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(0.dp)
                )
                Row(modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(36.dp)){
                    Column(
                        modifier = Modifier
                            .width(100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        Text(
                            text = stringResource(id = R.string.hours),
                            style = MaterialTheme.typography.labelMedium
                        )
                        HoursDropDownMenu(setValue = "0") { hours ->
                            hoursValue = hours.toInt()
                        }
                    }
                    Column(
                        modifier = Modifier
                            .width(100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        Text(
                            text = stringResource(id = R.string.minutes),
                            style = MaterialTheme.typography.labelMedium
                        )
                        MinutesDropDownMenu(setValue = "0") { minutes ->
                            minutesValue = minutes.toInt()
                        }
                    }
                }
                Button(
                    onClick = {
                        onDismissRequest()
                        submitButtonClick(
                            minutesValue + hoursValue * 60
                        )
                    },
                    modifier = Modifier
                        .padding(top = 0.dp)
                        .width(200.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Secondary,
                        contentColor = Black
                ),
                shape = RoundedCornerShape(24.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.add_time),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}