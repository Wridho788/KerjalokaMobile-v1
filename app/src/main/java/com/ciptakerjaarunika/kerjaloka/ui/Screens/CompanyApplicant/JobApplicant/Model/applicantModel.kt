package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.Model

import com.ciptakerjaarunika.kerjaloka.model.CompanyDetail.rating
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
    val application: application,
    val applicant: applicant,
    val ownRating: ownRating,
    val bookmarked: Boolean,
    val comment: List<CommentModel>,
    val jobApplicationHistory: List<List<jobApplicantHistory>>,
    val papiKostickResult: papiKostickResult_applicant,
    val qualified: List<qualified>
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
)

data class preferenceJobType(
    val jobTypeNo: Int,
    val jobTypeName: String,
)

data class qualified(
    val shortQuestionNo: Long,
    val mustHave: Boolean,
    val Qualified: Boolean
)

data class jobApplicantHistory(
    val applicationStatusHistory: Int,
    val jobPosition: String,
    val jobSeekerNo: Long,
    val applicationNo: Long,
    val lastUpdated: String
)

data class papiKostickResult_applicant(
    val PAPIKostickResult: PAPIKostickResult,
    val createdOn: String
)

data class documents(
    val documentNo: Long,
    val documentName: String,
    val documentFileName: String,
    val documentTypeNo: Int
)

data class PAPIKostickResult(
    val name: String,
    val needToFinishTask: Int = 3,
    val hardIntenseWorked: Int = 2,
    val needToAchieve: Int = 4,
    val leadership: Int = 5,
    val needToControlOthers: Int = 9,
    val easeInDecisionMaking: Int = 9,
    val pace: Int = 7,
    val vigorousType: Int = 5,
    val needForClosenessAndAffection: Int = 4,
    val needToBelongToGroups: Int = 5,
    val socialExtension: Int = 7,
    val needToBeNoticed: Int = 8,
    val organizedType: Int= 9 ,
    val interestInWorkingWithDetails: Int = 8,
    val theoreticalType: Int = 9,
    val needForChange: Int = 4,
    val emotionalResistant: Int = 3,
    val needToBeForceful: Int = 4,
    val needToSupportAuthority: Int = 4,
    val needForRulesAndSupervision: Int = 2,
)

data class ownRating(
    val ownUserRatingNo: Int,
    val ownRating: Int,
    val proRating: List<String>,
    val conRating: List<String>,
    val rating: rating,
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