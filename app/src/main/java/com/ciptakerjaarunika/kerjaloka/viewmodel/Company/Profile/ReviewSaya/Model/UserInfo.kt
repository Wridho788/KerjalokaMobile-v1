package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.ReviewSaya.Model


import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("ownerPhoto") val ownerPhoto: String,
    @SerializedName("rating") val rating: Float
)