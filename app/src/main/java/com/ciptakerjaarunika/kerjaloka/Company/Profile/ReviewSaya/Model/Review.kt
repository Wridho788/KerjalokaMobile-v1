package com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model


import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("approvedByUserNo")
    val approvedByUserNo: Int,
    @SerializedName("approvedOn")
    val approvedOn: String,
    @SerializedName("canAppeal")
    val canAppeal: Boolean,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("conRating")
    val conRating: List<String>,
    @SerializedName("ownerInfo")
    val ownerInfo: Any?,
    @SerializedName("proRating")
    val proRating: List<String>,
    @SerializedName("raterPhoto")
    val raterPhoto: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("ratingAt")
    val ratingAt: String,
    @SerializedName("userFullName")
    val userFullName: String,
    @SerializedName("userNo")
    val userNo: Long,
    @SerializedName("userRatingNo")
    val userRatingNo: Int,
    @SerializedName("userRole")
    val userRole: Int
)