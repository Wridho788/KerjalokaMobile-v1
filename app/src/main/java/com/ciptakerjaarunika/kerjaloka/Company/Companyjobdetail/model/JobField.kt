package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model


import com.google.gson.annotations.SerializedName

data class JobField(
    @SerializedName("fieldName")
    val fieldName: String,
    @SerializedName("fieldNo")
    val fieldNo: Int,
    @SerializedName("fieldParentNo")
    val fieldParentNo: Any?
)