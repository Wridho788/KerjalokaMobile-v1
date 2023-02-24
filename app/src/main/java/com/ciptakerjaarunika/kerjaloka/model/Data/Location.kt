package com.ciptakerjaarunika.kerjaloka.model.Data

data class Location(
    val locationsNo: Int,
    val city: String,
    val province: String,
    val country: String,
)

data class LocationFilter(
    val locationsNo: Int,
    val city: String,
    val province: String,
    val country: String,
    var checked: Boolean?
)

//data class sendLocation(
//    val jobNo: Long,
//    val cityNo: Int,
//    val jobLangitude: String,
//    val jobLatitude: String,
//)
