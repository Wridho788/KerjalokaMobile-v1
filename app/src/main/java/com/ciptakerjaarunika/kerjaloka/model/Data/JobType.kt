package com.ciptakerjaarunika.kerjaloka.model.Data

data class JobType(
    val jobTypeName : String,
    val jobTypeNo : Int,
)
data class JobTypeFilter(
    val jobTypeName : String,
    val jobTypeNo : Int,
    val checked : Boolean?
)
