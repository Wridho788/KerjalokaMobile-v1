package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model


import com.google.gson.annotations.SerializedName

data class JobResponses(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<DataActiveJob>
)