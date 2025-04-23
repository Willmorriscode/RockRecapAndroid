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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rockrecap.R
import com.rockrecap.data.RouteViewModel
import com.rockrecap.data.enums.getRouteGradeList
import com.rockrecap.ui.theme.Tertiary
import com.rockrecap.ui.theme.customTextFieldColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradeDropDownMenu(
    viewModel: RouteViewModel,
    grade: (String) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Text(
            text = stringResource(id = R.string.difficulty_rating),
            style = MaterialTheme.typography.labelMedium
        )

        var options: List<String>
        if (viewModel.routeTypeValue == "Boulder"){
            options = getRouteGradeList(1).map { it.text }
        }
        else if (viewModel.routeTypeValue == "Top Rope" || viewModel.routeTypeValue == "Lead Climb"){
            options = getRouteGradeList(2).map { it.text }
        }
        else { // fallback behavior to get all route types
            options = getRouteGradeList(3).map { it.text }
        }

        var expanded by remember { mutableStateOf(false) }
        var routeTypeValue by remember { mutableStateOf(viewModel.routeGradeValue) }

        ExposedDropdownMenuBox(modifier = Modifier
            .background(Tertiary),
            expanded = expanded,
            onExpandedChange = {
                if(viewModel.gradeSelectionIsEnabled){
                    expanded = !expanded
                }
            }) {

            routeTypeValue?.let {
                TextField(
                    modifier = Modifier.menuAnchor()
                        .fillMaxWidth(),
                    readOnly = true,
                    colors = customTextFieldColors(),
                    value = it,
                    onValueChange = { },
                    enabled = viewModel.gradeSelectionIsEnabled,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    isError = viewModel.routeGradeError,
                )
            }
            ExposedDropdownMenu(
                expanded = expanded, onDismissRequest = { expanded = false }
            ){
                options.forEach{ selectionOption ->
                    DropdownMenuItem(text = { Text(text = selectionOption) },
                        onClick = {
                            if(viewModel.gradeSelectionIsEnabled){
                                routeTypeValue = selectionOption
                                grade(selectionOption)
                                expanded = false
                            }
                        })
                }
            }
        }
        if (viewModel.routeGradeError) {
            Text(
                text = "Please select a route grade",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}