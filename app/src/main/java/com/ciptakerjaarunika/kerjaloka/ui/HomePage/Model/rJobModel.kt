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
//    val timeUploadApplicant: String,
    val logo: String,
    val status: String,
    val link: String,
    val CompanyName: String,
//    val CreatedOn : PrettyTime
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
    val JobDescription: String,
    val jobTitle: List<jobTitle>,
    val jobMinExperience: Int,
    val jobField: jobField,
    val jobRole: jobRole,
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
