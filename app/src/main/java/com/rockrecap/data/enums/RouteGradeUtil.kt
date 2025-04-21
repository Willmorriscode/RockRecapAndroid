package com.rockrecap.data.enums

enum class RouteGrade(val text: String) {
    UNSET("NA"),

    // boulder ratings
    VB("VB"),
    V1("V1"),
    V2("V2"),
    V3("V3"),
    V4("V4"),
    V5("V5"),
    V6("V6"),
    V7("V7"),
    V8("V8"),
    V9("V9"),
    V10("V10"),
    V11("V11"),

    // top rope/lead climb ratings
    R5_6("5.6"),
    R5_7("5.7"),
    R5_8("5.8"),
    R5_9("5.9"),
    R5_10("5.10"),
    R5_11("5.11"),
    R5_12("5.12"),
    R5_13("5.13"),
    R5_14("5.14"),
    R5_15("5.15");
}

// Will return a list of RouteGrades
// options = 1 : returns boulder grades
// options = 2 : returns top rope / lead climb grades
// options = 3 : all route grades
fun getRouteGradeList(option: Int): List<RouteGrade> {
    val routeGradeList = mutableListOf<RouteGrade>()

    routeGradeList.add(RouteGrade.UNSET)
    if(option == 1 || option == 3){
        routeGradeList.add(RouteGrade.VB)
        routeGradeList.add(RouteGrade.V1)
        routeGradeList.add(RouteGrade.V2)
        routeGradeList.add(RouteGrade.V3)
        routeGradeList.add(RouteGrade.V4)
        routeGradeList.add(RouteGrade.V5)
        routeGradeList.add(RouteGrade.V6)
        routeGradeList.add(RouteGrade.V7)
        routeGradeList.add(RouteGrade.V8)
        routeGradeList.add(RouteGrade.V9)
        routeGradeList.add(RouteGrade.V10)
        routeGradeList.add(RouteGrade.V11)
    }

    if(option == 2 || option == 3){
        routeGradeList.add(RouteGrade.R5_6)
        routeGradeList.add(RouteGrade.R5_7)
        routeGradeList.add(RouteGrade.R5_8)
        routeGradeList.add(RouteGrade.R5_9)
        routeGradeList.add(RouteGrade.R5_10)
        routeGradeList.add(RouteGrade.R5_11)
        routeGradeList.add(RouteGrade.R5_12)
        routeGradeList.add(RouteGrade.R5_13)
        routeGradeList.add(RouteGrade.R5_14)
        routeGradeList.add(RouteGrade.R5_15)
    }

    return routeGradeList
}