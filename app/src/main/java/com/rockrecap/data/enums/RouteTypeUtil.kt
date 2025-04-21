package com.rockrecap.data.enums

enum class RouteType(val text: String){
    BOULDER("Boulder"),
    TOP_ROPE("Top Rope"),
    LEAD_CLIMB("Lead Climb"),
}

fun getRouteTypeList(): List<RouteType> {
    val routeTypeList = mutableListOf<RouteType>()
    routeTypeList.add(RouteType.BOULDER)
    routeTypeList.add(RouteType.TOP_ROPE)
    routeTypeList.add(RouteType.LEAD_CLIMB)

    return routeTypeList
}
