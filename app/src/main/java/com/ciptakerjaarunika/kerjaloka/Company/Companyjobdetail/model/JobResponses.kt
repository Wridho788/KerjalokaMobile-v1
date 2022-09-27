package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model


import com.google.gson.annotations.SerializedName

data class JobResponses(
    @SerializedName("code")
    val code: String,
    @SerializedName("data")
    val `data`: List<Data>
)