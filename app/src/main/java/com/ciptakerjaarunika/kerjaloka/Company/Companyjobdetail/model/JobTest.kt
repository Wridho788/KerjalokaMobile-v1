package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model


import com.google.gson.annotations.SerializedName

data class JobTest(
    @SerializedName("companyNo")
    val companyNo: Long,
    @SerializedName("createdByUserNo")
    val createdByUserNo: Long,
    @SerializedName("createdOn")
    val createdOn: String,
    @SerializedName("isPublic")
    val isPublic: Boolean,
    @SerializedName("isTakedown")
    val isTakedown: Boolean,
    @SerializedName("limitReviewDay")
    val limitReviewDay: Any?,
    @SerializedName("maxScore")
    val maxScore: Int,
    @SerializedName("orderNo")
    val orderNo: Any?,
    @SerializedName("refOwner")
    val refOwner: Any?,
    @SerializedName("refTestNo")
    val refTestNo: Any?,
    @SerializedName("reviewedAmount")
    val reviewedAmount: Any?,
    @SerializedName("testDuration")
    val testDuration: Int,
    @SerializedName("testEnabled")
    val testEnabled: Boolean,
    @SerializedName("testHint")
    val testHint: Any?,
    @SerializedName("testLink")
    val testLink: String?,
    @SerializedName("testName")
    val testName: String,
    @SerializedName("testNo")
    val testNo: Int,
    @SerializedName("testPeriod")
    val testPeriod: Int
)