package com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("approved")
    var approved: Boolean,
    @SerializedName("approvedOn")
    var approvedOn: String?,
    @SerializedName("comment")
    var comment: String?,
    @SerializedName("conRating")
    var conRating: List<String>,
    @SerializedName("proRating")
    var proRating: List<String>,
    @SerializedName("raterPhoto")
    var raterPhoto: String?,
    @SerializedName("rating")
    var rating: Int,
    @SerializedName("ratingAt")
    var ratingAt: String,
    @SerializedName("ratingBy")
    var ratingBy: String?,
    @SerializedName("userFullName")
    var userFullName: String?,
    @SerializedName("userNo")
    var userNo: Long,
    @SerializedName("userRatingNo")
    var userRatingNo: Int
)