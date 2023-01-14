package com.ciptakerjaarunika.kerjaloka.model.CompanyDetail

import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyReview.Model.reviewList


data class company_detail_model(
    val code: Int,
    val errorCode: Int?,
    val message: String?,
    val data: company_detail_list,
)

data class company_detail_list(
    val logo: String,
    val companyName: String,
    val companyNo: Long,
    val field: String,
    val phone: String,
    val companyAddress: String,
    val size: String,
    val companyDescription: String,
    val location: location,
    val rating: rating,
    val job: List<job>,
    val link: String,
    val followers: Long,
    var followed: Boolean,
    var ownRating: Int?,
    var ownRatingAt: String?,
    var ownUserRatingNo: Int?,
    var ownRatingComment: String?,
    var ownProRating: List<String>?,
    var ownConRating: List<String>?,
)

data class location(
    val city: String,
    val province: String,
)

data class rating(
    val ratingValue: Float,
    val ratingList: List<reviewList>
)
data class jobLocation(
    val cityNo : Int,
    val location : String,
    val label : String
)
data class job(
    val jobNo: Long,
    val jobPosition: String,
    val jobLocation: List<jobLocation>,
    val createdOn: String,
    val company: company
)

data class company(
    val companyNo: Long,
    val companyName: String,
    val logo: String,
    val location: locationCompany
)

data class locationCompany(
    val city: String,
    val province: String,
    val country : String,
)