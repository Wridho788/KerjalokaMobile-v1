package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.model


import com.google.gson.annotations.SerializedName

data class JobExperienceLevel(
    @SerializedName("experienceLevelName")
    val experienceLevelName: String,
    @SerializedName("experienceLevelNo")
    val experienceLevelNo: Int
)