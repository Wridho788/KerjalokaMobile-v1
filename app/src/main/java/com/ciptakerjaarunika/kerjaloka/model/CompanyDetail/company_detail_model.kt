package com.ciptakerjaarunika.kerjaloka.model.CompanyDetail

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
)

data class location(
    val city: String,
    val province: String,
)

data class rating(
    val ratingValue: Float
)

data class job(
    val jobNo: Long,
    val jobPosition: String,
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
)