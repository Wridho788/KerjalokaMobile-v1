package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.dashboard.Model


import com.google.gson.annotations.SerializedName

data class TotalApplicantResponses(
    @SerializedName("code") var code: String, @SerializedName("data") var data: Int
)