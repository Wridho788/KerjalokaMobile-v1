package com.ciptakerjaarunika.kerjaloka.Company.Test

import com.google.gson.annotations.SerializedName

data class Model (
    @SerializedName("testname") val testname: String,
    @SerializedName("testduration") val testduration: String,
    @SerializedName("testperiod") val testperiod: String,
    @SerializedName("testcreator") val testcreator: String,
)
