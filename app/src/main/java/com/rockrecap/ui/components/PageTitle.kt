package com.rockrecap.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PageTitle(text: String){
    Text(
        modifier = Modifier
            .padding(vertical = 24.dp),
        text = text,
        style = MaterialTheme.typography.titleLarge
    )
}