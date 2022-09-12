package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.Model

data class relatedJobModel(
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