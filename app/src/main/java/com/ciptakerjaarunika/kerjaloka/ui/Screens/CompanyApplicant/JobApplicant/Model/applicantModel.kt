package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.Model

data class listApplicantResponse(
    val code: Int,
    val errorCode: Int,
    val message: String,
    val data: List<applicantModel>
)

data class applicantModel(
    val applicationNo: Long,
    val jobseekerNo: Long,
    val jobNo: Long,
    val application: application,
    val applicant: applicant,
)

data class application(
    val applicationNo: Long,
    val jobseekerNo: Long,
    val jobNo: Long,
    val applicationStatusNo: Int,
    val applyOn: String
)

data class applicant(
    val jobseekerNo: Long,
    val name: String,
    val location: locationApplicant,
    val photo: String,
)

data class locationApplicant(
    val city: String,
    val province: String,
)

data class location(
    val label: String
)