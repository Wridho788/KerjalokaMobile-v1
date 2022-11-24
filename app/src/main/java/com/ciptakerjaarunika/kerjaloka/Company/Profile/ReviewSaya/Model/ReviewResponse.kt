package com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model


import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("message")
    val message: String
)
