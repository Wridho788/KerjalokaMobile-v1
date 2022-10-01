package com.ciptakerjaarunika.kerjaloka.Company.dashboard.Model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("applicationStatusNo")
    var applicationStatusNo: Int,
    @SerializedName("date")
    var date: String,
    @SerializedName("jobNo")
    var jobNo: Long,
    @SerializedName("jobPosition")
    var jobPosition: String,
    @SerializedName("jobseekerNo")
    var jobseekerNo: Long
)