package com.rockrecap.ui.pages

import android.graphics.Typeface
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.ProgressIndicatorDefaults.drawStopIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.Insets
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.rockrecap.R
import com.rockrecap.ui.components.PageTitle
import com.rockrecap.ui.theme.RockOutline
import com.rockrecap.ui.theme.Tertiary
import com.rockrecap.data.RouteViewModel
import com.rockrecap.data.UserRouteStatistics
import com.rockrecap.data.enums.RouteCompleteStatus
import com.rockrecap.data.enums.RouteType
import com.rockrecap.data.enums.getRouteTypeList
import com.rockrecap.ui.theme.RockBrown
import com.rockrecap.ui.theme.ChartBlue
import com.rockrecap.ui.theme.ChartGray
import com.rockrecap.ui.theme.RockBlue
import com.rockrecap.ui.theme.RockRed
import com.rockrecap.ui.theme.filterChipTextFieldColors
import com.rockrecap.utilities.formatCompletionFloat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.floor

@Composable
fun StatisticsPage(navController: NavHostController, viewModel: RouteViewModel){

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val userStatistics = remember { mutableStateOf<UserRouteStatistics?>(null)}

    LaunchedEffect(Unit){
        userStatistics.value = viewModel.getUserStatistics()
    }

    userStatistics.value?.let { stats ->
        // Use stats.totalCompletionStats.completed, etc.
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                PageTitle(stringResource(id = R.string.statistics), true)

                var allRouteTypesString = stringResource(R.string.all_route_types)
                FilterDropdownMenu(allRouteTypesString){ routeType ->
                    if(routeType == allRouteTypesString){
                        viewModel.updateRouteFilter(null)
                    }
                    else{
                        viewModel.updateRouteFilter(RouteType.entries.first { it.text == routeType})
                    }
                }
            }
            SimpleSummaryStatistics(stats, viewModel)
            RouteCompletionStatistics(stats, viewModel)
            RouteGraphedStatistics(stats, viewModel, coroutineScope)
            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

@Composable
fun RouteCompletionStatistics(stats: UserRouteStatistics, viewModel: RouteViewModel) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(288.dp),
        colors = CardDefaults.cardColors(
            containerColor = Tertiary
        ),
        shape = RoundedCornerShape(22.dp)
    ){
        Column(modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
            .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Column(modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                val completionRateFloat = viewModel.getRouteCompletionRateByRouteType(stats)
                Text(
                    text = stringResource(id = R.string.route_completion_rate),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    LinearProgressIndicator(
                        progress = { completionRateFloat },
                        modifier = Modifier.fillMaxWidth().height(6.dp),
                        color = ChartBlue,
                        trackColor = ChartGray,
                        strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                        gapSize = 0.dp,
                        drawStopIndicator = {
                            drawStopIndicator(
                                // this will draw an invisible stop cap, we don't want it to display
                                drawScope = this,
                                stopSize = 0.dp,
                                color = ChartGray,
                                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap
                            )
                        }
                    )
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                    ){
                        Box(modifier = Modifier
                            .padding(start = (getPercentagePosition(completionRateFloat)).dp)){
                            Text(
                                text = formatCompletionFloat(completionRateFloat),
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
            Row(modifier = Modifier
                .fillMaxHeight(),
                horizontalArrangement = Arrangement.spacedBy(62.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Column(modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ){
                    Column(modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(modifier = Modifier
                            .width(100.dp),
                            text = stringResource(id = R.string.routes_completed),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            text = viewModel.getRoutesCompletedByRouteType(stats),
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
                Column(modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ){
                    Column(modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .width(100.dp),
                            text = stringResource(id = R.string.routes_attempted),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            text = viewModel.getRoutesAttemptedByRouteType(stats),
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

fun getPercentagePosition(completionRateFloat: Float): Float {
val position =
        if(completionRateFloat.toDouble() == 1.0){
            275.0
        }
        else if (completionRateFloat >= 0.8)
    {
        260.0
    }
    else if (completionRateFloat <= 0.1)
    {
        0.0
    } else if (completionRateFloat > 0.5 && completionRateFloat < 0.8)
    {
        completionRateFloat * 20/20 * 300
    }
    else if(completionRateFloat < 0.5 && completionRateFloat > 0.1)
    {
        completionRateFloat * 17/20 * 300
    }
    else
    {
        // 0.5 completed uses this number
        completionRateFloat * 295
    }

    return floor(position.toFloat())
}

@Composable
fun RouteGraphedStatistics(stats: UserRouteStatistics, viewModel: RouteViewModel, coroutineScope: CoroutineScope) {

    val xAxisFormatter =
        CartesianValueFormatter { context, x, _ ->
            val gradeList = viewModel.getRouteGradeListBasedOnFilter()
            val index = x.toInt()
            if (gradeList != null && index in gradeList.indices) {
                gradeList[index].text
            } else {
                "#" // placeholder avoids crash
            }
        }

    Card(modifier = Modifier
        .fillMaxSize()
        .height(400.dp),
        colors = CardDefaults.cardColors(
            containerColor = Tertiary
        ),
        shape = RoundedCornerShape(22.dp)
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            if(viewModel.routeFilter.value == null){
                Text(
                    text = "Switch the route filter to view breakdowns of specific route grades",
                    textAlign = TextAlign.Center
                )
            }
            else{
                // container rendering and holding the chart and other text
                Column(modifier =
                    Modifier
                        .fillMaxWidth(17/20f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    Column(modifier = Modifier
                        .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ){
                        Text(
                            text = when(viewModel.routeFilter.value){
                                RouteType.BOULDER -> stringResource(id = R.string.boulder_route_graph_label)
                                RouteType.TOP_ROPE -> stringResource(id = R.string.top_rope_route_graph_label)
                                RouteType.LEAD_CLIMB -> stringResource(id = R.string.lead_climb_route_graph_label)
                                null -> ""
                            },
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                        )

                        Box(){ // holds graphs
                            val modelProducer = remember { CartesianChartModelProducer() }
                            LaunchedEffect(viewModel.routeFilter.value) {
                                modelProducer.runTransaction {
                                    columnSeries { series(viewModel.getFormattedGradeList(
                                        completeStatus = RouteCompleteStatus.COMPLETED,
                                        stats = viewModel.getFilteredCompletedGradeStatistics(stats)
                                    )) }
                                }
                            }
                            CartesianChartHost(
                                rememberCartesianChart(
                                    rememberColumnCartesianLayer(
                                        ColumnCartesianLayer.ColumnProvider.series(
                                            rememberLineComponent(fill = fill(Color(ChartBlue.toArgb())), thickness = 6.dp)
                                        )
                                    ),
                                    startAxis = VerticalAxis.rememberStart(
                                        title = "Y-Axis Grade Completion Counts",
                                        tickLength = 3.dp,
                                        itemPlacer = VerticalAxis.ItemPlacer.step(
                                            step = {1.0}
                                        ),
                                        label = TextComponent(
                                            typeface = Typeface.DEFAULT,
                                            padding = Insets(0f),
                                            margins = Insets(startDp = 0f, endDp = 6f, topDp = 0f, bottomDp = 0f),
                                        )

                                    ),
                                    bottomAxis = HorizontalAxis.rememberBottom(
                                        title = "X-Axis Grade Values",
                                        tickLength = 3.dp,
                                        valueFormatter = xAxisFormatter,
                                        guideline = null,
                                        labelRotationDegrees = 270.0f,
                                        label = TextComponent(
                                            typeface = Typeface.DEFAULT,
                                            padding = Insets(0f),
                                            margins = Insets(startDp = 0f, endDp = 0f, topDp = 6f, bottomDp = 0f),

                                        )
                                    ),

                                ),
                                modelProducer,
                            )
                        }
                        Text(
                            text = when(viewModel.routeFilter.value){
                                RouteType.BOULDER -> stringResource(id = R.string.boulder_route_graph_xlabel)
                                RouteType.TOP_ROPE -> stringResource(id = R.string.top_rope_route_graph_xlabel)
                                RouteType.LEAD_CLIMB -> stringResource(id = R.string.lead_climb_route_graph_xlabel)
                                null -> ""
                            },
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SimpleSummaryStatistics(stats: UserRouteStatistics, viewModel: RouteViewModel) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp),
    ){
        Card(
            modifier = Modifier
                .width(114.dp)
                .height(166.dp),
            colors = CardDefaults.cardColors(
                containerColor = Tertiary
            ),
            shape = RoundedCornerShape(18.dp)
        ){
            Column(
                modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Column(modifier = Modifier
                    .height(65.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(modifier = Modifier,
                        text = stringResource(id = R.string.total_time_climbing),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                }
                Text(
                    text = viewModel.getTotalClimbingTimeByRouteType(stats),
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                )
            }

        }
        Card(
            modifier = Modifier
                .width(114.dp)
                .height(166.dp),
            colors = CardDefaults.cardColors(
                containerColor = Tertiary
            ),
            shape = RoundedCornerShape(18.dp)
        ){
            Column(
                modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = stringResource(id = R.string.shortest_route_time),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = viewModel.getShortestCompletedRouteTimeByRouteType(stats),
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                )
            }

        }
        Card(
            modifier = Modifier
                .width(114.dp)
                .height(166.dp),
            colors = CardDefaults.cardColors(
                containerColor = Tertiary
            ),
            shape = RoundedCornerShape(18.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = stringResource(id = R.string.longest_route_time),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = viewModel.getLongestCompletedRouteTimeByRouteType(stats),
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                )
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropdownMenu(setValue:String, routeType: (String) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){

        val options = getRouteTypeList().map { it.text }
        var expanded by remember { mutableStateOf(false) }
        var routeTypeValue by remember { mutableStateOf(setValue) }

        val noFilterText = stringResource(R.string.all_route_types)

        ExposedDropdownMenuBox(modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Tertiary),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }) {

            TextField(
                modifier = Modifier.menuAnchor()
                    .width(180.dp)
                    .height(50.dp)
                    .border(
                        width = 1.dp,
                        color = RockOutline,
                        shape = RoundedCornerShape(16.dp)
                    ),
                readOnly = true,
                value = routeTypeValue,
                onValueChange = { },
                textStyle = MaterialTheme.typography.bodySmall,
                trailingIcon = {
                    Row {
                        // Clear "X" icon
                        if (routeTypeValue != noFilterText && !expanded) {
                            IconButton(
                                onClick = {
                                    routeTypeValue = noFilterText
                                    routeType("All Route Types") // Reset callback to default string
                                    expanded = false
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear",
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        else{
                            // Dropdown arrow
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded,
                            )
                        }
                        }},
                colors = filterChipTextFieldColors(),
            )
            ExposedDropdownMenu(modifier = Modifier
                .background(Tertiary),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ){
                options.forEach{ selectionOption ->
                    DropdownMenuItem(text = { Text(text = selectionOption) },
                        onClick = {
                            routeTypeValue = selectionOption
                            routeType(selectionOption)
                            expanded = false
                        })
                }
            }
        }
    }
}