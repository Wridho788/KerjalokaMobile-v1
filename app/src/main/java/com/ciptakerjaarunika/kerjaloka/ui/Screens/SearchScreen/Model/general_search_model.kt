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
    val location: location,
)

data class location(
    val locationText: String,
)

data class companyList(
    val companyName: String,
    val fieldName: String,
    val locationText: String,
//    val logo?: String,
)