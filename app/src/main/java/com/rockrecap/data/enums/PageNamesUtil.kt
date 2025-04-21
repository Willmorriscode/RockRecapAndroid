package com.rockrecap.data.enums

enum class PageNames(val text: String){
    ACTIVE_ROUTES("Active Routes"),
    INACTIVE_ROUTES("Inactive Routes"),
    ADD_NEW_ROUTE("Add New Route"),
    YOUR_STATISTICS("Your Statistics")
}

fun getPageNamesList(): List<PageNames> {
    var pageNamesList = mutableListOf<PageNames>()

    pageNamesList.add(PageNames.ACTIVE_ROUTES)
    pageNamesList.add(PageNames.INACTIVE_ROUTES)
    pageNamesList.add(PageNames.ADD_NEW_ROUTE)
    pageNamesList.add(PageNames.YOUR_STATISTICS)

    return pageNamesList
}