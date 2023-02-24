package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.JobDetailScreen.Model


data class company(
    val companyNo: Long, val companyName: String, val logo: String, val location: locationCompany
)

data class locationCompany(
    val city: String,
    val province: String,
)