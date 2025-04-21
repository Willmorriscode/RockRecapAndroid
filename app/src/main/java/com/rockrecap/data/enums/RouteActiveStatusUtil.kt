package com.rockrecap.data.enums

import androidx.compose.ui.graphics.Color
import com.rockrecap.data.enums.RouteActiveStatus.ACTIVE
import com.rockrecap.data.enums.RouteActiveStatus.INACTIVE
import com.rockrecap.ui.theme.RockBlue
import com.rockrecap.ui.theme.RockGray

enum class RouteActiveStatus(val text: String, val color: Color) {
    ACTIVE(
        text = "Active",
        color = RockBlue,
    ),
    INACTIVE(
        text = "Inactive",
        color = RockGray,
    );
}

fun getRouteActiveStatusList(): List<RouteActiveStatus> {
    val statusList = mutableListOf<RouteActiveStatus>()
    statusList.add(ACTIVE)
    statusList.add(INACTIVE)

    return statusList
}
