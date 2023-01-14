package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ListApplicant.Model

data class company_officer_jobs_response(
    val code: Int,
    val errorCode: Int,
    val message: String,
    val data: List<listApplicantJobModel>
)

data class cvBank_response(val code: Int, val data: Int)

data class listApplicantJobModel(
    val jobNo: Long,
    val jobPosition: String,
    val createdOn: String,
    val createdBy: String?,
    var publish: Boolean,
    )

data class jobType(
    val jobTypeNo: Int,
    val jobTypeName : String,
)


data class jobField(val fieldName: String)

