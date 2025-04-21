package com.rockrecap.data.navigation

sealed class Destination(val route:String) {
    object ActiveRoutes : Destination(
        route = "active_routes",
    )
    object InactiveRoutes : Destination(
        route = "inactive_routes",
    )
    object RouteDisplay : Destination(
        route = "route_display",
    )
    object AddRoute : Destination(
        route = "add_route",
    )
    object EditRoute : Destination(
        route = "edit_route",
    )
    object Statistics : Destination(
        route = "statistics",
    )

    companion object{
        val toList = listOf(ActiveRoutes, InactiveRoutes, RouteDisplay, AddRoute, EditRoute, Statistics)
    }
}