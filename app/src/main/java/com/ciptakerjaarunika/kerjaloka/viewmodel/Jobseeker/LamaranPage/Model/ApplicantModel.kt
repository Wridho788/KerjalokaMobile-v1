package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.LamaranPage.Model

import com.google.gson.annotations.SerializedName

data class ApplicantModel(
    @SerializedName("jobposition") val jobposition: String,
    @SerializedName("companyname") val companyname: String,
    @SerializedName("companyaddress") val companyaddress: String,
    @SerializedName("totaltest") val totaltest: Int,
    @SerializedName("taketest") val taketest: Int,
    @SerializedName("requirentment") val requirentment: String,
)


