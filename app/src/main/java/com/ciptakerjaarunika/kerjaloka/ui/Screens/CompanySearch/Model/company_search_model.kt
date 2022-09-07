package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model

data class search_company_response(
    val code: Int,
    val errorCode: Int?,
    val message: String?,
    val data: List<search_company_model>
)

//data class company_model(
//    val companyList: List<search_company_model>
//)


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