package com.rockrecap.utilities

import android.annotation.SuppressLint
import com.rockrecap.data.Route
import com.rockrecap.data.enums.RouteActiveStatus
import com.rockrecap.data.enums.RouteColor
import com.rockrecap.data.enums.RouteCompleteStatus
import com.rockrecap.data.enums.RouteGrade
import com.rockrecap.data.enums.RouteType
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

// Will format the amount of time logged on a route
fun formatRouteTime(time: Int): String {
    val days = time / 1440 // 1440 minutes in a day
    val remainingMinutesAfterDays = time % 1440
    val hours = remainingMinutesAfterDays / 60 // 60 minutes in an hour
    val minutes = remainingMinutesAfterDays % 60

    return buildString {
        if (days > 0) {
            append("${days}d ")
        }
        if (hours > 0) {
            append("${hours}h ")
        }
        if (minutes > 0 || (days == 0 && hours == 0)) {
            append("${minutes}m")
        }
    }.trim() // Remove any trailing spaces
}

// Will format the date logged on a route
fun formatRouteDate(timestamp: Long, option: Int): String {
    val formatter = when (option) {
        1 -> SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
        2 -> SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        else -> throw IllegalArgumentException("Invalid option: $option")
    }
    return formatter.format(Date(timestamp))
}

fun formatCompletionFloat(completionRate: Float): String{
    if(completionRate.toDouble() == 0.0){
        return "0%"
    }
    else{
        return "${(completionRate*100).roundToInt()}%" // formats the result to be a string in '##.#%' format
    }
}

// used as the starting routes for when the database is generated
fun getSeedRoutes(): List<Route> {
    return listOf(
            // ---- BOULDER ----
            Route(name = "Boulder Bash", summary = "Short boulder problem with a powerful move", type = RouteType.BOULDER, color = RouteColor.RED, grade = RouteGrade.V3_5, timeLogged = 300, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1713811200000L, routeBelongsTo = 1, routeTemplateCreatedBy = 100),
            Route(name = "Slab Sunday", summary = "Technical slab requiring balance", type = RouteType.BOULDER, color = RouteColor.BLUE, grade = RouteGrade.V1_3, timeLogged = 200, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.INCOMPLETE, startDate = 1713811200000L, routeBelongsTo = 1, routeTemplateCreatedBy = 101),
            Route(name = "Dyno Dynasty", summary = "Big jumps and commitment", type = RouteType.BOULDER, color = RouteColor.TAN, grade = RouteGrade.V4_6, timeLogged = 250, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.INCOMPLETE, startDate = 1714070400000L, routeBelongsTo = 3, routeTemplateCreatedBy = 103),
            Route(name = "Pebble Puzzler", summary = "Crimpy face climbing", type = RouteType.BOULDER, color = RouteColor.GREEN, grade = RouteGrade.V1_3, timeLogged = 270, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1714156800000L, routeBelongsTo = 1, routeTemplateCreatedBy = 104),
            Route(name = "Mantle Madness", summary = "Slopers and mantles", type = RouteType.BOULDER, color = RouteColor.YELLOW, grade = RouteGrade.V1_3, timeLogged = 220, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1714243200000L, routeBelongsTo = 2, routeTemplateCreatedBy = 105),
            Route(name = "Blue Cave Route", summary = "Overhanging prow", type = RouteType.BOULDER, color = RouteColor.BLUE, grade = RouteGrade.V3_5, timeLogged = 310, activeStatus = RouteActiveStatus.ACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1745304005894L, routeBelongsTo = 2, routeTemplateCreatedBy = 106),
            Route(name = "Campus Circuit", summary = "Campus board-style moves", type = RouteType.BOULDER, color = RouteColor.WHITE, grade = RouteGrade.V2_4, timeLogged = 350, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1714416000000L, routeBelongsTo = 3, routeTemplateCreatedBy = 107),
            Route(name = "Crux Candy", summary = "One-move wonder", type = RouteType.BOULDER, color = RouteColor.ORANGE, grade = RouteGrade.V2_4, timeLogged = 280, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1714502400000L, routeBelongsTo = 3, routeTemplateCreatedBy = 108),
            Route(name = "Grippy Gremlin", summary = "Sharp crimps", type = RouteType.BOULDER, color = RouteColor.GRAY, grade = RouteGrade.V4_6, timeLogged = 240, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.INCOMPLETE, startDate = 1714588800000L, routeBelongsTo = 3, routeTemplateCreatedBy = 109),
            Route(name = "Hueco Hustle", summary = "Juggy roof", type = RouteType.BOULDER, color = RouteColor.BROWN, grade = RouteGrade.V0_2, timeLogged = 180, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.INCOMPLETE, startDate = 1714675200000L, routeBelongsTo = 1, routeTemplateCreatedBy = 110),
            Route(name = "Mystery Mountain", summary = "No idea what the topout looks like yet", type = RouteType.BOULDER, color = RouteColor.WHITE, grade = RouteGrade.V2_4, timeLogged = 180, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1714675200000L, routeBelongsTo = 1, routeTemplateCreatedBy = 110),


            // ---- LEAD_CLIMB ----
            Route(name = "Lead Legends", summary = "Overhung with dynamic moves", type = RouteType.LEAD_CLIMB, color = RouteColor.GREEN, grade = RouteGrade.R5_10, timeLogged = 1800, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.INCOMPLETE, startDate = 1713897600000L, routeBelongsTo = 2, routeTemplateCreatedBy = 102),
            Route(name = "Overhang Ordeal", summary = "Pumpy sequence", type = RouteType.LEAD_CLIMB, color = RouteColor.RED, grade = RouteGrade.R5_11, timeLogged = 200, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1714761600000L, routeBelongsTo = 2, routeTemplateCreatedBy = 111),
            Route(name = "Overhang Ordeal", summary = "Pumpy sequence", type = RouteType.LEAD_CLIMB, color = RouteColor.RED, grade = RouteGrade.R5_11, timeLogged = 200, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1714761600000L, routeBelongsTo = 2, routeTemplateCreatedBy = 111),
            Route(name = "Slab Scare", summary = "Runout slab", type = RouteType.LEAD_CLIMB, color = RouteColor.YELLOW, grade = RouteGrade.R5_9, timeLogged = 150, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1714848000000L, routeBelongsTo = 2, routeTemplateCreatedBy = 112),
            Route(name = "Crack Climber", summary = "Jam city", type = RouteType.LEAD_CLIMB, color = RouteColor.ORANGE, grade = RouteGrade.R5_8, timeLogged = 140, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1714934400000L, routeBelongsTo = 2, routeTemplateCreatedBy = 113),
            Route(name = "Whip Watch", summary = "Mental game test", type = RouteType.LEAD_CLIMB, color = RouteColor.BLUE, grade = RouteGrade.R5_10, timeLogged = 160, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.INCOMPLETE, startDate = 1715020800000L, routeBelongsTo = 2, routeTemplateCreatedBy = 114),
            Route(name = "Power Pitch", summary = "Full throttle roof", type = RouteType.LEAD_CLIMB, color = RouteColor.PURPLE, grade = RouteGrade.R5_7, timeLogged = 190, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1715107200000L, routeBelongsTo = 3, routeTemplateCreatedBy = 115),
            Route(name = "Groove Line", summary = "Technical face with rests", type = RouteType.LEAD_CLIMB, color = RouteColor.PURPLE, grade = RouteGrade.R5_9, timeLogged = 155, activeStatus = RouteActiveStatus.ACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1745241440377L, routeBelongsTo = 3, routeTemplateCreatedBy = 116),
            Route(name = "Stalactite Slayer", summary = "Unique features", type = RouteType.LEAD_CLIMB, color = RouteColor.GRAY, grade = RouteGrade.R5_10, timeLogged = 130, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1715280000000L, routeBelongsTo = 3, routeTemplateCreatedBy = 117),
            Route(name = "El Cap Teaser", summary = "Long and exhausting", type = RouteType.LEAD_CLIMB, color = RouteColor.WHITE, grade = RouteGrade.R5_12, timeLogged = 210, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.INCOMPLETE, startDate = 1715366400000L, routeBelongsTo = 2, routeTemplateCreatedBy = 118),
            Route(name = "Traverse Tango", summary = "Technical horizontal moves", type = RouteType.LEAD_CLIMB, color = RouteColor.GREEN, grade = RouteGrade.R5_7, timeLogged = 125, activeStatus = RouteActiveStatus.ACTIVE, completedStatus = RouteCompleteStatus.INCOMPLETE, startDate = 1745178874860L, routeBelongsTo = 1, routeTemplateCreatedBy = 119),
            Route(name = "Tricky Towers", summary = "Technical CRAZY dyno!", type = RouteType.LEAD_CLIMB, color = RouteColor.PURPLE, grade = RouteGrade.R5_7, timeLogged = 125, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.INCOMPLETE, startDate = 1715452800000L, routeBelongsTo = 1, routeTemplateCreatedBy = 119),


            // ---- TOP_ROPE ----
            Route(name = "Top Rope Tango", summary = "Endurance with rests", type = RouteType.TOP_ROPE, color = RouteColor.YELLOW, grade = RouteGrade.R5_10, timeLogged = 120, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.INCOMPLETE, startDate = 1713984000000L, routeBelongsTo = 2, routeTemplateCreatedBy = 100),
            Route(name = "Juggernaut", summary = "Jug haul on a slab", type = RouteType.TOP_ROPE, color = RouteColor.BLUE, grade = RouteGrade.R5_8, timeLogged = 100, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1715539200000L, routeBelongsTo = 3, routeTemplateCreatedBy = 120),
            Route(name = "Balance Beam", summary = "Thin feet and slabs", type = RouteType.TOP_ROPE, color = RouteColor.GREEN, grade = RouteGrade.R5_9, timeLogged = 110, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1715625600000L, routeBelongsTo = 1, routeTemplateCreatedBy = 121),
            Route(name = "Chalk Storm", summary = "Heavily trafficked route", type = RouteType.TOP_ROPE, color = RouteColor.RED, grade = RouteGrade.R5_9, timeLogged = 110, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1715712000000L, routeBelongsTo = 1, routeTemplateCreatedBy = 122),
            Route(name = "Vertical Limit", summary = "Sustained vertical section", type = RouteType.TOP_ROPE, color = RouteColor.RED, grade = RouteGrade.R5_10, timeLogged = 120, activeStatus = RouteActiveStatus.ACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1745116309342L, routeBelongsTo = 2, routeTemplateCreatedBy = 123),
            Route(name = "Smooth Operator", summary = "Silky holds, bad feet", type = RouteType.TOP_ROPE, color = RouteColor.PURPLE, grade = RouteGrade.R5_10, timeLogged = 130, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1715884800000L, routeBelongsTo = 2, routeTemplateCreatedBy = 124),
            Route(name = "Rest Day Warmup", summary = "Mellow warmup line", type = RouteType.TOP_ROPE, color = RouteColor.GRAY, grade = RouteGrade.R5_7, timeLogged = 90, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1715971200000L, routeBelongsTo = 2, routeTemplateCreatedBy = 125),
            Route(name = "Techy Tendons", summary = "Fingery and thin", type = RouteType.TOP_ROPE, color = RouteColor.BLACK, grade = RouteGrade.R5_11, timeLogged = 140, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1716057600000L, routeBelongsTo = 3, routeTemplateCreatedBy = 126),
            Route(name = "Rope Rodeo", summary = "Weird clipping crux", type = RouteType.TOP_ROPE, color = RouteColor.WHITE, grade = RouteGrade.R5_12, timeLogged = 150, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.INCOMPLETE, startDate = 1716144000000L, routeBelongsTo = 3, routeTemplateCreatedBy = 127),
            Route(name = "Slippery Slope", summary = "Greasy holds!", type = RouteType.TOP_ROPE, color = RouteColor.YELLOW, grade = RouteGrade.R5_8, timeLogged = 95, activeStatus = RouteActiveStatus.ACTIVE, completedStatus = RouteCompleteStatus.INCOMPLETE, startDate = 1745053743825L, routeBelongsTo = 1, routeTemplateCreatedBy = 128),
            Route(name = "Mousetrap", summary = "Strange sit start. Beware", type = RouteType.TOP_ROPE, color = RouteColor.YELLOW, grade = RouteGrade.R5_9, timeLogged = 95, activeStatus = RouteActiveStatus.INACTIVE, completedStatus = RouteCompleteStatus.COMPLETED, startDate = 1716230400000L, routeBelongsTo = 1, routeTemplateCreatedBy = 128)

    )
}

