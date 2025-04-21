package com.rockrecap.data.enums

import androidx.compose.ui.graphics.Color
import com.rockrecap.ui.theme.RockGreen
import com.rockrecap.ui.theme.RockYellow

enum class RouteCompleteStatus(val text: String, val color: Color) {
    COMPLETED(
        text = "Complete",
        color = RockGreen,
    ),
    INCOMPLETE(
        text = "Incomplete",
        color = RockYellow
    );
}

fun getRouteCompleteStatusList(): List<RouteCompleteStatus> {
    val statusList = mutableListOf<RouteCompleteStatus>()
    statusList.add(RouteCompleteStatus.COMPLETED)
    statusList.add(RouteCompleteStatus.INCOMPLETE)

    return statusList
}

