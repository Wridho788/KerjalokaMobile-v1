package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model


import com.google.gson.annotations.SerializedName

data class JobType(
    @SerializedName("jobTypeName")
    val jobTypeName: String,
    @SerializedName("jobTypeNo")
    val jobTypeNo: Int
)