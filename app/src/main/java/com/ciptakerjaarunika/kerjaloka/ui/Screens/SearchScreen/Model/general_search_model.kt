package com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Model


data class search_model(
    val code: Int,
    val errorCode: Int,
    val message: String,
    val data: general_search_model,
)

data class general_search_model(
    val jobList : List<jobList>,
    val companyList : List<companyList>
)

data class jobList(
    val jobPosition: String,
    val companyName: String,
    val link: String,
    val photo: String,
    val createdOn: String,
    val jobLocations: List<location>,
    val companyNo: Long,
    val jobNo: Long
)

data class location(
    val location: String,
)

data class companyList(
    val companyNo: Long,
    val companyName: String,
    val fieldName: String,
    val locationText: String,
    val logo: String,
)