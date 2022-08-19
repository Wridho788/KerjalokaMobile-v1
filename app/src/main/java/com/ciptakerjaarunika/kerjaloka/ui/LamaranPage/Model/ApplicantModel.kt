package com.ciptakerjaarunika.kerjaloka.ui.LamaranPage.Model

import com.google.gson.annotations.SerializedName

data class ApplicantModel {
        @SerializedName("jobposition") val jobposition: Long,
    @SerializedName("companyname") val companyname: String,
    @SerializedName("companyaddress") val companyaddress: String,
    @SerializedName("totaltest") val totaltest: Int,
    @SerializedName("taketest") val taketest: Int,
}