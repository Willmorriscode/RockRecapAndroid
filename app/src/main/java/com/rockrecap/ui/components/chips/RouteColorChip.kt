package com.rockrecap.ui.components.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.rockrecap.data.enums.RouteColor
import com.rockrecap.ui.theme.RockOutline

@Composable
fun RouteColorChip(status: RouteColor){
    Box(modifier = Modifier
        .height(24.dp)
        .clip(RoundedCornerShape(24.dp))
        .background(status.backgroundColor)
        .then(
            if (status == RouteColor.WHITE){
                Modifier.border(
                    width = 1.dp,
                    color = RockOutline,
                    shape = RoundedCornerShape(24.dp)
                )
            }
            else{
                Modifier // no border drawn
            }
        )
        .padding(horizontal = 12.dp)
        .wrapContentSize()
        ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ){
            Text(
                modifier = Modifier,
                text = status.text,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}