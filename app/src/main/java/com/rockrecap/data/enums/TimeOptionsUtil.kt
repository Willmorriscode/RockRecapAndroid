package com.rockrecap.data.enums

enum class MinuteOptions(val value: String){
    ZERO("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY_FIVE("45")
}

enum class HourOptions(val value: String){
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    ELEVEN("11"),
    TWELVE("12"),
    THIRTEEN("13"),
    FOURTEEN("14"),
    FIFTEEN("15"),
    SIXTEEN("16"),
    SEVENTEEN("17"),
    EIGHTEEN("18"),
    NINETEEN("19"),
    TWENTY("20"),
    TWENTY_ONE("21"),
    TWENTY_TWO("22"),
    TWENTY_THREE("23"),
    TWENTY_FOUR("24"),
    TWENTY_FIVE("25"),
    TWENTY_SIX("26"),
    TWENTY_SEVEN("27"),
    TWENTY_EIGHT("28"),
    TWENTY_NINE("29"),
    THIRTY("30"),
    THIRTY_ONE("31"),
    THIRTY_TWO("32"),
    THIRTY_THREE("33"),
    THIRTY_FOUR("34"),
    THIRTY_FIVE("35"),
    THIRTY_SIX("36"),
    THIRTY_SEVEN("37"),
    THIRTY_EIGHT("38"),
    THIRTY_NINE("39"),
    FORTY("40"),
    FORTY_ONE("41"),
    FORTY_TWO("42"),
    FORTY_THREE("43"),
    FORTY_FOUR("44"),
    FORTY_FIVE("45"),
    FORTY_SIX("46"),
    FORTY_SEVEN("47"),
    FORTY_EIGHT("48"),
    FORTY_NINE("49"),
    FIFTY("50")
}

fun getMinuteOptionsList(): List<MinuteOptions> {
    val minutesList = mutableListOf<MinuteOptions>()

    minutesList.add(MinuteOptions.FORTY_FIVE)
    minutesList.add(MinuteOptions.THIRTY)
    minutesList.add(MinuteOptions.FIFTEEN)
    minutesList.add(MinuteOptions.ZERO)

    return minutesList
}

fun getAllHoursOptionsList(): List<HourOptions> {
    return enumValues<HourOptions>().toList()
}


fun getLowHoursOptionsList(): List<HourOptions>{
    val hoursList = mutableListOf<HourOptions>()

    hoursList.add(HourOptions.SIX)
    hoursList.add(HourOptions.FIVE)
    hoursList.add(HourOptions.FOUR)
    hoursList.add(HourOptions.THREE)
    hoursList.add(HourOptions.TWO)
    hoursList.add(HourOptions.ONE)
    hoursList.add(HourOptions.ZERO)

    return hoursList
}