package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model

import com.beust.klaxon.Klaxon

private val klaxon = Klaxon()

data class ResponseJobs(
    val jobNo: String,
    val jobPosition: String,
    val jobDescription: String,
    val link: String,
    val jobType: JobType?,
    val jobExperienceLevel: JobExperienceLevel?,
    val jobCity: List<JobCity>,
    val jobSalaryMax: Any? = null,
    val jobSalaryMin: Long,
    val jobField: JobField?,
    val jobRole: JobRole?,
    val jobMinExperience: Long,
    val jobSkills: List<JobSkill>?,
    val jobTitle: List<JobTitle>?,
    val createdOn: String,
    val createdBy: String,
    val publish: Boolean,
    val jobShortQuestion: List<JobShortQuestion>?,
    val jobTests: List<JobTest>?,
    val expired: String,
    val takedown: Boolean,
    val jobAdditionalDescription: Any? = null,
    val packageName: Any? = null
)

data class JobExperienceLevel (
    val experienceLevelNo: Long,
    val experienceLevelName: String
)

data class JobField (
    val fieldNo: Long,
    val fieldName: String,
    val fieldParentNo: Any? = null
)

data class JobRole (
    val jobRoleNo: Long,
    val jobRoleName: String,
    val fieldNo: Long
)

data class JobShortQuestion (
    val shortQuestionNo: Long,
    val companyNo: Long,
    val shortQuestion: String,
    val enabled: Boolean,
    val createdBy: Long,
    val createdOn: String,
    val questionType: Long,
    val updatedOn: Any? = null,
    val updatedBy: Any? = null,
    val shortQuestionCategoryNo: Any? = null
)

data class JobSkill (
    val jobSkillNo: Long,
    val jobNo: Long,
    val skillNo: Long,
    val skillName: String
)

data class JobTest (
    val testNo: Long,
    val companyNo: Long,
    val testName: String,
    val testDuration: Long,
    val testPeriod: Long,
    val maxScore: Long,
    val testEnabled: Boolean,
    val isTakedown: Boolean,
    val createdByUserNo: Long,
    val createdOn: String,
    val testHint: String,
    val reviewedAmount: Any? = null,
    val limitReviewDay: Any? = null,
    val refOwner: Any? = null,
    val refTestNo: Any? = null,
    val testLink: String,
    val isPublic: Boolean,
    val isSpecial: Boolean,
    val orderNo: Any? = null
)

data class JobTitle (
    val jobTitleNo: Long,
    val jobNo: Long,
    val titleNo: Long,
    val titleName: String
)

data class JobType (
    val jobTypeNo: Long,
    val jobTypeName: String
)

data class JobCity (
    val id: Int,
    val cityname:String
        )

data class item (
    val jobNo: Long,
    val companyNo: Long,
    val jobPosition: String,
    val jobDescription: Any? = null,
    val jobRequirements: String,
    val jobTypeNo: Long,
    val jobExperienceLevelNo: Any? = null,
    val jobSalaryMin: Any? = null,
    val jobSalaryMax: Any? = null,
    val jobFieldNo: Any? = null,
    val jobRoleNo: Any? = null,
    val isHideCompany: Boolean,
    val jobMinExperience: Any? = null,
    val expired: String,
    val publish: Boolean,
    val takedown: Boolean,
    val createdBy: Long,
    val createdOn: String,
    val isHideSalary: Boolean,
    val packageNo: Long,
    val editCounter: Long,
    val isDeleted: Boolean,
    val viewCount: Long,
    val isSystem: Boolean,
    val link: String,
    val orderNo: Long,
    val publishedOn: String,
    val updatedOn: String
)
data class analytic (
    val clickCount: Long,
    val totalDuration: Long,
    val averageDuration: Long
)
