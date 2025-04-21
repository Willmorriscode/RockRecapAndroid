package com.rockrecap.data.enums

import androidx.compose.ui.res.stringResource
import com.rockrecap.R

enum class RouteDisplayPageMenuOptions(val text: String){
    EDIT_ROUTE("Edit Route"),
    DELETE_ROUTE("Delete Route"),
    ACTIVATE_ROUTE("Activate"),
    DEACTIVATE_ROUTE("Deactivate"),
    MARK_COMPLETE("Mark Complete"),
    MARK_INCOMPLETE("Mark Incomplete"),
}

fun getRouteDisplayPageMenuOptionsList(activeStatus: RouteActiveStatus, completeStatus: RouteCompleteStatus): List<RouteDisplayPageMenuOptions>{
    val menuOptionsList = mutableListOf<RouteDisplayPageMenuOptions>()

    if(activeStatus == RouteActiveStatus.INACTIVE){
        menuOptionsList.add(RouteDisplayPageMenuOptions.ACTIVATE_ROUTE)
    }
    else{
        menuOptionsList.add(RouteDisplayPageMenuOptions.DEACTIVATE_ROUTE)
    }

    if(completeStatus == RouteCompleteStatus.COMPLETED && activeStatus == RouteActiveStatus.ACTIVE) {
        menuOptionsList.add(RouteDisplayPageMenuOptions.MARK_INCOMPLETE)
    }

    if(activeStatus == RouteActiveStatus.ACTIVE){
        menuOptionsList.add(RouteDisplayPageMenuOptions.EDIT_ROUTE)
    }

    menuOptionsList.add(RouteDisplayPageMenuOptions.DELETE_ROUTE)

    return menuOptionsList
}