package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.ReviewSaya.Model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("reviewList") val reviewList: List<Review>,
    @SerializedName("userInfo") val userInfo: UserInfo
)