package com.rockrecap.ui.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rockrecap.ui.theme.Black
import com.rockrecap.ui.theme.LightText
import com.rockrecap.ui.theme.Tertiary

@Composable
fun BackButton(onBackSelected: () -> Unit){
    Button(
        onClick = onBackSelected,
        modifier = Modifier
            .padding(top = 8.dp)
            .height(35.dp)
            .width(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Tertiary, // Use theme's primary color
            contentColor = Black   // Use theme's onPrimary color
        ),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(0.dp),  // <-- Remove internal padding
    ) {
        Row (modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back Button",
                modifier = Modifier.size(18.dp),
                tint = LightText
            )
//            Text(text = stringResource(R.string.back),
//                style = MaterialTheme.typography.bodySmall)
        }
    }
}