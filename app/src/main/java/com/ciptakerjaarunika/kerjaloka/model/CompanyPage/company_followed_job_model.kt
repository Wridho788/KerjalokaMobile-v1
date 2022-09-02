package com.ciptakerjaarunika.kerjaloka.model.CompanyPage

data class company_followed_job_model(
    val code: Int,
    val errorCode : Int?,
    val message : String?,
    val data: List<company_followed_list>,
)

data class company_followed_list(
    val companyName: String,
    val companyNo: Long,
    val field: String,
    val logo: String,
    val location: locationFollowedJob,
    val followed: Boolean
)

data class locationFollowedJob(
    val city: String,
    val province: String,
)


