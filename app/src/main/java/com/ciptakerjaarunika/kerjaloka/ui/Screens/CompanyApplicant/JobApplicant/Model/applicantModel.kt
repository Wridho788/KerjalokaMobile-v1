package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.Model

import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionHistory.Model.HistoryModel
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionKomentar.Model.CommentModel


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
    val ownRating: ownRating,
    val papiKostickResult: papiKostickResult_applicant
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
    val preferenceJobType: List<preferenceJobType>,
    val expectedSalary: Int,
    val experiences: List<experience>,
    val record: List<record>,
    val education: List<education>,
    val documents: List<documents>,
    val comments: List<CommentModel>,
    val history: List<HistoryModel>
)

data class preferenceJobType(
    val jobTypeNo: Int,
    val jobTypeName: String,
)

data class papiKostickResult_applicant(
    val PAPIKostickResult: PAPIKostickResult,
    val createdOn: String
)

data class documents(
    val documentNo: Long,
    val documentName: String,
    val documentFileName : String,
    val documentTypeNo: Int
)

data class PAPIKostickResult(
    val name: String,
    val needToFinishTask: String,
    val hardIntenseWorked: String,
    val needToAchieve: String,
    val leadership: String,
    val needToControlOthers: String,
    val easeInDecisionMaking: String,
    val pace: String,
    val vigorousType: String,
    val needForClosenessAndAffection: String,
    val needToBelongToGroups: String,
    val socialExtension: String,
    val needToBeNoticed: String,
    val organizedType: String,
    val interestInWorkingWithDetails: String,
    val theoreticalType: String,
    val needForChange: String,
    val emotionalResistant: String,
    val needToBeForceful: String,
    val needToSupportAuthority: String,
    val needForRulesAndSupervision: String,
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
    val recordNo: Int,
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