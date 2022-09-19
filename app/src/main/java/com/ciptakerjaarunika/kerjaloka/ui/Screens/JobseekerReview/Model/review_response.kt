package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobseekerReview.Model

data class review_response(
    val companyNo: Long,
    val companyName: String,
    val raterphoto: String,
//    val userRole :  Long,
//    val comment: String,
    val rating: Float,
    val ratingAt: String,
    val messageReview: String,
//    val proRating: List<String>,
//    val conRating: List<String>
)

data class my_review(
    val companyNo: Long,
    val companyName: String,
    val raterphoto: String,
    val userRole :  Long,
    val comment: String,
    val rating: Float,
    val ratingAt: String,
    val messageReview: String,
    val proRating: List<String>,
    val conRating: List<String>
)