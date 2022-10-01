package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model


import com.google.gson.annotations.SerializedName

data class JobRole(
    @SerializedName("fieldNo")
    val fieldNo: Int,
    @SerializedName("jobRoleName")
    val jobRoleName: String,
    @SerializedName("jobRoleNo")
    val jobRoleNo: Int
)