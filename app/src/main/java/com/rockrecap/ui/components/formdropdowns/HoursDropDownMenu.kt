package com.rockrecap.ui.components.formdropdowns

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import com.rockrecap.data.enums.getLowHoursOptionsList
import com.rockrecap.ui.theme.Tertiary
import com.rockrecap.ui.theme.customTextFieldColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HoursDropDownMenu(setValue: String, hours: (String) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        val options = getLowHoursOptionsList().map { it.value }
        var expanded by remember { mutableStateOf(false) }
        var selectedValue by remember { mutableStateOf(setValue) }

        ExposedDropdownMenuBox(modifier = Modifier
            .background(Tertiary),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }) {

            TextField(
                modifier = Modifier.menuAnchor()
                    .width(140.dp),
                readOnly = true,
                value = selectedValue,
                colors = customTextFieldColors(),
                onValueChange = { },
                textStyle = MaterialTheme.typography.bodyMedium,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            )
            ExposedDropdownMenu(
                modifier = Modifier
                    .background(Tertiary),
                expanded = expanded, onDismissRequest = { expanded = false }
            ){
                options.forEach{ selectionOption ->
                    DropdownMenuItem(text = { Text(text = selectionOption) },
                        onClick = {
                            selectedValue = selectionOption
                            hours(selectionOption)
                            expanded = false
                        })
                }
            }
        }
    }
}