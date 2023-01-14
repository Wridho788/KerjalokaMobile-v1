package com.ciptakerjaarunika.kerjaloka.model.Profile


data class JobseekerEducationsResponse(
    val code: Int,
    val data: List<JobseekerEducations>,
)

data class JobseekerEducations(
    val educationBeginAt: String,
    val educationCityName: String,
    val educationCityNo: Int,
    val educationCountry: String,
    val educationDescription: String,
    val educationEndedAt: String,
    val educationMajorName: String,
    val educationMajorNo: Int,
    val educationNo: Long,
    val educationProvinceName: String,
    val educationSchool: String,
    val educationTitleName: String,
    val educationTitleNo: Int,
    val gpa: Double?,
    val jobseekerEducationNo: Long,
    val jobseekerNo: Long
)

data class JobseekerEducationsRequest(
    val jobseekerEducationNo: Long?,
    val jobseekerNo: Long?,
    val educationSchool: String?,
    val educationBeginAt: String?,
    val educationEndedAt: String?,
    var educationMajorNo: Int?,
    var educationTitleNo: Int?,
    var educationCityNo: Int?,
    val gpa: Double?,
    val educationDescription: String?,
)

