package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.JobseekerReview.Model

data class review_response(
    val code: Int, val errorCode: Int?, val message: String?, val data: company_reviews
)

data class company_reviews(
    val userInfo: userInfo, val reviewList: List<reviewList>
)

data class userInfo(
    val ownerPhoto: String,
    val name: String,
    val rating: Long,
    val email: String,
)

data class reviewList(
    val userFullName: String,
    val userNo: Long,
    val userRole: Long,
    val comment: String,
    val rating: Float,
    val ratingAt: String,
    val raterPhoto: String,
    val approvedOn: String,
    val proRating: List<String>,
    val conRating: List<String>
)

data class send(
    val hasSend: Boolean,
    val canSend: Boolean,
)

data class send_Request(
    val userNo: Long,
    val message: String,
    val rating: Long,
    val proRating: ArrayList<proRatingList>,
    val conRating: ArrayList<conRatingList>
)

data class proRatingList(
    val categoryNo: Long,
)

data class conRatingList(
    val categoryNo: Long
)

data class sendResponse(
    val code: Int,
    val errorCode: Int,
    val message: String,
    val data: String,
)