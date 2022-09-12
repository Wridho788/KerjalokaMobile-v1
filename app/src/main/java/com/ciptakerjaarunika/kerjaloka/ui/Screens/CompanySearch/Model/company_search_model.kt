package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model

data class search_company_response(
    val code: Int,
    val errorCode: Int?,
    val message: String?,
    val data: List<searchRequest>
)

data class search_company_model(
    val companyNo: Long,
    val companyName: String,
    val field: String,
    val logo: String,
    val location: location
)

data class location(
    val city: String,
    val province: String,
)

data class searchRequest(
    val query: String,
    val location: List<location_model>,
    val industri: List<industri_model>,
    val sizeCompany: List<size_company_model>,
    val user: String?,

)