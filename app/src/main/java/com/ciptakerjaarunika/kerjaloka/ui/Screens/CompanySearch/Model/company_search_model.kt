package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model

data class search_company_response(
    val code: Int,
    val errorCode: Int?,
    val message: String?,
    val data: List<search_company_model>
)

data class search_company_model(
    val companyNo: Long,
    val companyName: String,
    val field: String,
    val logo: String,
    val location: location,
    val followed : Boolean
)

data class location(
    val city: String,
    val province: String,
)

data class searchCompanyRequest(
    var keyword: String?,
    var location: List<Int>,
    var industry: List<Int>,
    var size: List<Int>,
)