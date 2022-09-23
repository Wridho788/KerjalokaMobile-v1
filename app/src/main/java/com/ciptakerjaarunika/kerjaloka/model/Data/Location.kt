package com.ciptakerjaarunika.kerjaloka.model.Data

data class Location(
    val locationNo : Int,
    val city: String,
    val province : String,
    val country : String,
)

data class LocationFilter(
    val locationNo : Int,
    val city: String,
    val province : String,
    val country : String,
    val checked : Boolean?
)
