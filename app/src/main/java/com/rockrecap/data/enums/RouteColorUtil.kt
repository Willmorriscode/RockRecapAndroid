package com.rockrecap.data.enums

import androidx.compose.ui.graphics.Color
import com.rockrecap.ui.theme.RockBlack
import com.rockrecap.ui.theme.RockBlack50
import com.rockrecap.ui.theme.RockBlue
import com.rockrecap.ui.theme.RockBlue50
import com.rockrecap.ui.theme.RockBrown
import com.rockrecap.ui.theme.RockBrown50
import com.rockrecap.ui.theme.RockGray
import com.rockrecap.ui.theme.RockGray50
import com.rockrecap.ui.theme.RockGreen
import com.rockrecap.ui.theme.RockGreen50
import com.rockrecap.ui.theme.RockOrange
import com.rockrecap.ui.theme.RockOrange50
import com.rockrecap.ui.theme.RockPink
import com.rockrecap.ui.theme.RockPink50
import com.rockrecap.ui.theme.RockPurple
import com.rockrecap.ui.theme.RockPurple50
import com.rockrecap.ui.theme.RockRed
import com.rockrecap.ui.theme.RockRed50
import com.rockrecap.ui.theme.RockWhite
import com.rockrecap.ui.theme.RockYellow
import com.rockrecap.ui.theme.RockYellow50

enum class RouteColor(val text: String, val color: Color, val backgroundColor: Color) {
    WHITE(
        text = "White",
        color = RockWhite,
        backgroundColor = RockWhite // we don't make the chip any different color here
    ),
    PINK(
        text = "Pink",
        color = RockPink,
        backgroundColor = RockPink50
    ),
    RED(
        text = "Red",
        color = RockRed,
        backgroundColor = RockRed50
    ),
    ORANGE(
        text = "Orange",
        color = RockOrange,
        backgroundColor = RockOrange50
    ),
    YELLOW(
        text = "Yellow",
        color = RockYellow,
        backgroundColor = RockYellow50
    ),
    GREEN(
        text = "Green",
        color = RockGreen,
        backgroundColor = RockGreen50
    ),
    BLUE(
        text = "Blue",
        color = RockBlue,
        backgroundColor = RockBlue50
    ),
    PURPLE(
        text = "Purple",
        color = RockPurple,
        backgroundColor = RockPurple50
    ),
    GRAY(
        text = "Gray",
        color = RockGray,
        backgroundColor = RockGray50
    ),
    BLACK(
        text = "Black",
        color = RockBlack,
        backgroundColor = RockBlack50
    ),
    BROWN(
        text = "Brown",
        color = RockBrown,
        backgroundColor = RockBrown50
    );
}

fun getRouteColorList(): List<RouteColor> {
    val colorList = mutableListOf<RouteColor>()
    colorList.add(RouteColor.WHITE)
    colorList.add(RouteColor.PINK)
    colorList.add(RouteColor.RED)
    colorList.add(RouteColor.ORANGE)
    colorList.add(RouteColor.YELLOW)
    colorList.add(RouteColor.GREEN)
    colorList.add(RouteColor.BLUE)
    colorList.add(RouteColor.PURPLE)
    colorList.add(RouteColor.GRAY)
    colorList.add(RouteColor.BLACK)
    colorList.add(RouteColor.BROWN)

    return colorList
}