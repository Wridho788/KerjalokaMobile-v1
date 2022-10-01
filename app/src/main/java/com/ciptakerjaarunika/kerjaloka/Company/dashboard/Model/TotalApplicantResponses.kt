package com.ciptakerjaarunika.kerjaloka.Company.dashboard.Model


import com.google.gson.annotations.SerializedName

data class TotalApplicantResponses(
    @SerializedName("code")
    var code: String,
    @SerializedName("data")
    var `data`: Int
)