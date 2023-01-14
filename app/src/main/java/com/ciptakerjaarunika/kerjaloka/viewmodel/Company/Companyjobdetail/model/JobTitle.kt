package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.model


import com.google.gson.annotations.SerializedName

data class JobTitle(
    @SerializedName("jobNo")
    val jobNo: Long,
    @SerializedName("jobTitleNo")
    val jobTitleNo: Int,
    @SerializedName("titleName")
    val titleName: String,
    @SerializedName("titleNo")
    val titleNo: Int
)