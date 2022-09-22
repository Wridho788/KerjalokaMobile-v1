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
    val ownRating: ownRating
)

data class application(
    val applicationNo: Long,
    val jobseekerNo: Long,
    val jobNo: Long,
    val applicationStatusNo: Int,
    val applyOn: String,
    val message: String
)

data class applicant(
    val jobseekerNo: Long,
    val name: String,
    val location: locationApplicant,
    val photo: String,
    val expectedSalary: Int,
    val experiences: List<experience>,
    val record: List<record>,
    val education: List<education>,
)

data class ownRating(
    val ownUserRatingNo: Int,
    val ownRating: Int,
    val proRating: List<String>,
    val conRating: List<String>
)

data class education(
    val jobseekerEducationNo: Long,
    val educationBeginAt: String,
    val educationEndedAt: String,
    val educationMajorName: String,
    val educationSchool: String,
    val educationCityName: String,
    val educationCountry: String,
    val gpa: Float,
)

data class record(
    val name: String,
)

data class experience(
    val experienceNo: Long,
    val experiencePosition: String,
    val experienceCompanyName: String,
    val experienceCityName: String,
    val experienceCountry: String,
    val experienceEndedAt: String,
    val experienceBeginAt: String
)

data class locationApplicant(
    val city: String,
    val province: String,
)

data class location(
    val label: String
)