package com.rockrecap.ui.components.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rockrecap.R
import com.rockrecap.ui.theme.Black
import com.rockrecap.ui.theme.Secondary

@Composable
fun ViewInactiveRoutesButton(onClickViewRoute: () -> Unit){
    Column(modifier = Modifier
        .fillMaxWidth()
    ){
        Button(
            onClick = onClickViewRoute,
            modifier = Modifier
                .padding(16.dp)
                .align(CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Secondary, // Use theme's primary color
                contentColor = Black   // Use theme's onPrimary color
            )
        ) {
            Text(
                text = stringResource(id = R.string.view_inactive_routes),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}