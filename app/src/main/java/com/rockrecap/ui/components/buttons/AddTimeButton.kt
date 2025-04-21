package com.rockrecap.ui.components.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rockrecap.R
import com.rockrecap.ui.theme.Black
import com.rockrecap.ui.theme.RockGray
import com.rockrecap.ui.theme.RockGreen
import com.rockrecap.ui.theme.Secondary

@Composable
fun AddTimeButton(onClick: () -> Unit, isDisabled: Boolean = false){
    Button(
        onClick = onClick,
        modifier = Modifier
               .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Secondary, // Use theme's primary color
            contentColor = Black   // Use theme's onPrimary color
        ),
        enabled = isDisabled
    ) {
        Row(modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(20.dp),
                painter = painterResource(id = R.drawable.clock),
                contentDescription = "Clock Icon",
                colorFilter = if(isDisabled) {ColorFilter.tint(Black)} else {ColorFilter.tint(RockGray)}
            )
            Text(
                text = stringResource(id = R.string.add_time),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}