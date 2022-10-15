package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.Model

data class company_officer_jobs_response(
    val code: Int,
    val errorCode: Int,
    val message: String,
    val data: List<listApplicantJobModel>
)

data class cvBank_response(val code: Int, val data: Int)

data class listApplicantJobModel(
    val jobNo: String,
    val jobPosition: String,
    val createdOn: String,
    var publish: Boolean
)
