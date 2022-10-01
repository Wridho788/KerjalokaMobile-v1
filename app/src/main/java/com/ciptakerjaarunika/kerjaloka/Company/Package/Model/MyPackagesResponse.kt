package com.ciptakerjaarunika.kerjaloka.Company.Package.Model


import com.google.gson.annotations.SerializedName

data class MyPackagesResponse(
    @SerializedName("code")
    val code: String,
    @SerializedName("data")
    val `data`: List<Data>
)