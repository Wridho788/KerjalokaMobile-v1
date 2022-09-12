package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model

data class review_response(
    val code: Int,
    val errorCode: Int?,
    val message: String?,
    val data: company_reviews
)

data class company_reviews(
    val userInfo: userInfo,
    val reviewList: List<reviewList>
)

data class userInfo(
    val ownerPhoto: String,
    val name: String,
    val rating: Long,
    val email: String,
)

data class reviewList(
    val userFullName: String,
    val userNo :  Long,
    val userRole :  Long,
    val comment: String,
    val rating: Long,
    val ratingAt: String,
    val raterPhoto: String,
    val proRating: ArrayList<String>,
    val conRating: ArrayList<String>
)