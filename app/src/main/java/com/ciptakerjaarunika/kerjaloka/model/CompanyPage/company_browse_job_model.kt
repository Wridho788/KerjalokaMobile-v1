package com.ciptakerjaarunika.kerjaloka.model.CompanyPage

data class company_browse_job_model(
    val code: Int,
    val errorCode: Int?,
    val message: String?,
    val data: List<company_browse_list>,
)

data class company_browse_list(
    val companyName: String,
    val companyNo: Long,
    val userFullname: String,
    val field: String,
    val logo: String,
    val location: locationSearch
)

data class locationSearch(
    val city: String,
    val province: String,
)


