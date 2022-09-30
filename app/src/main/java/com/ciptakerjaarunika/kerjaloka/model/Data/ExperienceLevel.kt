package com.ciptakerjaarunika.kerjaloka.model.Data

data class ExperienceLevel(
    val experienceLevelNo : Int,
    val experienceLevelName : String,
)

data class ExperienceLevelFilter(
    val experienceLevelNo : Int,
    val experienceLevelName : String,
    var checked : Boolean?,
)

