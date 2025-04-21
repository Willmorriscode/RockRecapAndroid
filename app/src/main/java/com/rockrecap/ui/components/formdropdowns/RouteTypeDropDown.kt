package com.rockrecap.ui.components.formdropdowns

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rockrecap.R
import com.rockrecap.data.enums.getRouteTypeList
import com.rockrecap.ui.theme.Tertiary
import com.rockrecap.ui.theme.customTextFieldColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteTypeDropDownMenu(
    setValue:String,
    routeType: (String) -> Unit,
){
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Text(
            text = stringResource(id = R.string.route_type),
            style = MaterialTheme.typography.labelMedium
        )

        val options = getRouteTypeList().map { it.text }
        var expanded by remember { mutableStateOf(false) }
        var selectedValue by remember { mutableStateOf(setValue) }

        ExposedDropdownMenuBox(modifier = Modifier
            .background(Tertiary),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }) {

            TextField(
                modifier = Modifier.menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                value = selectedValue,
                onValueChange = {},
                colors = customTextFieldColors(),
                textStyle = MaterialTheme.typography.bodyMedium,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            )
            ExposedDropdownMenu(modifier = Modifier
                .background(Tertiary),
                expanded = expanded, onDismissRequest = { expanded = false }
            ){
                options.forEach{ selectionOption ->
                    DropdownMenuItem(text = { Text(text = selectionOption) },
                        onClick = {
                            selectedValue = selectionOption
                            routeType(selectionOption)
                            expanded = false
                        })
                }
            }
        }
    }
}