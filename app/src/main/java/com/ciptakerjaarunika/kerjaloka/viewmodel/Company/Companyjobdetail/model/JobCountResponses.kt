package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.model


import com.google.gson.annotations.SerializedName

data class JobCountResponses(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<DataCount>
)