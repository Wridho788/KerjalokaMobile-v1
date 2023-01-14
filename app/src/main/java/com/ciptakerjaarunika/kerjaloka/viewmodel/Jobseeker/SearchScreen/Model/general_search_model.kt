package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.SearchScreen.Model


data class search_model(
    val code: Int,
    val errorCode: Int,
    val message: String,
    val data: general_search_model,
)

data class top_search_model(
    val code: Int,
    val errorCode: Int,
    val message: String,
    val data: List<list_top_search>
)

data class list_top_search(
    val keyword: String,
    val count: Int,
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
    val jobNo: Long,
    var bookmarked: Boolean
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