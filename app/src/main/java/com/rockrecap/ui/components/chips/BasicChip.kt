package com.rockrecap.ui.components.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.rockrecap.ui.theme.Tertiary

@Composable
fun BasicChip(chipText: String){
    Box(modifier = Modifier
        .height(24.dp)
        .clip(RoundedCornerShape(24.dp))
        .background(Tertiary)
        .padding(horizontal = 12.dp)
        .wrapContentSize()
        ){
        Text(
            modifier = Modifier,
            text = chipText,
            style = MaterialTheme.typography.labelSmall
        )
    }
}