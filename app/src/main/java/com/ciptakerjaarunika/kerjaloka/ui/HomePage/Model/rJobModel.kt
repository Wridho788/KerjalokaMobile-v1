package com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model

data class rjob_model(
    val code: Int,
    val errorCode: Int,
    val message: String,
    val data: List<rJobModel>,
)

data class rJobModel(
    val companyNo: Long,
    val jobNo: Long,
    val jobPosition: String,
    val jobLocation: String,
    val logo: String,
    val status: String,
    val link: String,
    val companyName: String,
    val createdOn: String,
)

data class rJobDetailResponse(
    val code: Int,
    val errorCode: Int,
    val message: String,
    val data: rJobDetailModel
)

data class rJobDetailModel(
    val companyNo: Long,
    val jobNo: Long,
    val jobPosition: String,
    val jobLocation: List<jobLocation>,
    val link: String,
    val company: company,
    val jobDescription: String,
    val jobTitle: List<jobTitle>,
    val jobMinExperience: Int,
    val jobField: jobField?,
    val jobRole: jobRole?,
    val createdOn: String,
    )

data class jobLocation(
    val label: String,
)
data class jobField(val fieldName: String)
data class jobTitle(val titleName: String)
data class jobRole(val jobRoleName: String)
data class company(
    val logo: String,
    val companyName: String,
)
