package com.rockrecap.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.rockrecap.data.Route
import com.rockrecap.data.enums.RouteCompleteStatus
import com.rockrecap.ui.theme.RockGreen
import com.rockrecap.ui.theme.Tertiary
import com.rockrecap.ui.theme.RockOutline

@Composable
fun RouteListCard(route: Route, onCardClick: () -> Unit) {
// main container for the card
    Card (
        onClick = onCardClick,
        modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)
        .height(80.dp),
            colors = CardDefaults.cardColors(
            containerColor = Tertiary
            )
    ){
        // row inside the card
        Row (modifier = Modifier
            .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            // row containing the left half of the card
            Row(modifier = Modifier
                .padding(),
                verticalAlignment = Alignment.CenterVertically
            ){
                // container with circle
                Box(modifier = Modifier
                    .width(72.dp)
                ){
                    Canvas(
                    modifier = Modifier.fillMaxSize(),
                    ) {
                        drawCircle(
                            color = route.color.color,
                            radius = 56f,
                        )
                        drawCircle(
                            color = RockOutline,
                            radius = 56f,
                            style = Stroke(width = 3f)
                        )
                    }
                }
                // route name and rating container
                Box (modifier = Modifier
                    ,contentAlignment = Alignment.Center
                ) {
                    Column (
                        modifier = Modifier,
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                    ){
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(7/10f),
                            text = route.name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            modifier = Modifier,
                            text = "${route.type.text} • ${route.grade.text}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            // time logged container
            Row(modifier = Modifier
                .padding(end = 16.dp)
            ) {
                if(route.completedStatus == RouteCompleteStatus.COMPLETED){
                    Image(
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(24.dp),
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Clock Icon",
                        colorFilter = ColorFilter.tint(RockGreen)
                    )
                }
                else{
//                    Image(
//                        modifier = Modifier
//                            .padding(end = 4.dp)
//                            .size(18.dp),
//                        painter = painterResource(id = R.drawable.clock),
//                        contentDescription = "Clock Icon",
//                        colorFilter = ColorFilter.tint(LightText)
//                    )
//                    Text(
//                        text = formatRouteTime(route.timeLogged),
//                        style = MaterialTheme.typography.bodySmall,
//                    )
                }
            }
        }
    }
}
