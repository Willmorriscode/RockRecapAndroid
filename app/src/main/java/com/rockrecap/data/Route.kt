package com.rockrecap.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rockrecap.data.enums.RouteActiveStatus
import com.rockrecap.data.enums.RouteColor
import com.rockrecap.data.enums.RouteCompleteStatus
import com.rockrecap.data.enums.RouteGrade
import com.rockrecap.data.enums.RouteType

@Entity(tableName = "routes")
data class Route(
    @PrimaryKey(autoGenerate = true)
    val routeId: Int = 0,
    val name: String = "",
    val summary: String = "",
    val type: RouteType = RouteType.BOULDER,
    val color: RouteColor = RouteColor.WHITE,
    val grade: RouteGrade = RouteGrade.UNSET,
    val timeLogged: Int = 0, // stores logged minutes
    val activeStatus: RouteActiveStatus = RouteActiveStatus.ACTIVE,
    val completedStatus: RouteCompleteStatus = RouteCompleteStatus.INCOMPLETE,
    val startDate: Long = 0L,  // number of milliseconds since epoch, ie January 1, 1970, 00:00:00 UTC
    val routeBelongsTo: Int = 0, // user ID for who the copy of the route belongs to when logging and editing data
    val routeTemplateCreatedBy: Int = 0, // user ID for who created the template others could copy
    // the last two fields here are possibly going to be used in the future, perhaps not
)
