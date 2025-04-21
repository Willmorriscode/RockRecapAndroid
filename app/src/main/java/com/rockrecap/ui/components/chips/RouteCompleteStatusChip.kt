package com.rockrecap.ui.components.chips

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.rockrecap.data.enums.RouteCompleteStatus
import com.rockrecap.ui.theme.RockOutline
import com.rockrecap.ui.theme.Tertiary

@Composable
fun RouteCompleteStatusChip(status: RouteCompleteStatus){
    Box(modifier = Modifier
        .height(24.dp)
        .clip(RoundedCornerShape(24.dp))
        .background(Tertiary)
        .padding(horizontal = 12.dp)
        .wrapContentSize()
        ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ){
            Box(modifier = Modifier
                .size(18.dp)
            ){
                Canvas(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    drawCircle(
                        color = status.color,
                        radius = 18f,
                    )
                    drawCircle(
                        color = RockOutline,
                        radius = 16f,
                        style = Stroke(width = 2f)
                    )
                }
            }
            Text(
                modifier = Modifier
                    .height(18.dp),
                text = status.text,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}