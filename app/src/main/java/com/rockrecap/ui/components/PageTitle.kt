package com.rockrecap.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PageTitle(text: String, smallerSize: Boolean = false){
    Text(
        modifier = Modifier
            .padding(vertical = 24.dp)
            .then(
                if(!smallerSize){ // conditionally will let the text container fit the size of the text
                    Modifier.fillMaxWidth(4/5f)
                }
                else{
                    Modifier
                }
            ),
        text = text,
        style = MaterialTheme.typography.titleLarge
    )
}