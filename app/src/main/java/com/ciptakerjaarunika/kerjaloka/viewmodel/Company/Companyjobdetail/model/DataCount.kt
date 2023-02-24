package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.model


import com.ciptakerjaarunika.kerjaloka.model.Data.JobType
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class DataCount(
    @SerializedName("createdBy") val createdBy: String,
    @SerializedName("createdOn") val createdOn: String,
    @SerializedName("expired") val expired: String,
    @SerializedName("jobAdditionalDescription") val jobAdditionalDescription: Any?,
    @SerializedName("jobCity") val jobCity: Array<String>,
    @SerializedName("jobDescription") val jobDescription: String?,
    @SerializedName("jobExperienceLevel") val jobExperienceLevel: JobExperienceLevel?,
    @SerializedName("jobField") val jobField: JobField?,
    @SerializedName("jobMinExperience") val jobMinExperience: Int?,
    @SerializedName("jobNo") val jobNo: Long,
    @SerializedName("jobPosition") val jobPosition: String,
    @SerializedName("jobRole") val jobRole: JobRole?,
    @SerializedName("jobSalaryMax") val jobSalaryMax: BigDecimal?,
    @SerializedName("jobSalaryMin") val jobSalaryMin: BigDecimal?,
    @SerializedName("jobShortQuestion") val jobShortQuestion: List<JobShortQuestionCount>,
    @SerializedName("jobSkills") val jobSkills: List<JobSkill>,
    @SerializedName("jobTests") val jobTests: List<JobTest>,
    @SerializedName("jobTitle") val jobTitle: List<JobTitle>,
    @SerializedName("jobType") val jobType: JobType,
    @SerializedName("link") val link: String,
    @SerializedName("publish") val publish: Boolean,
    @SerializedName("takedown") val takedown: Boolean
)

data class JobShortQuestionCount(
    val shortQuestionNo: Long,
    val companyNo: Long,
    val shortQuestion: String,
    val enabled: Boolean,
    val createdBy: Long,
    val createdOn: String,
    val questionType: Long,
    val updatedOn: String? = null,
    val updatedBy: Long? = null,
    val shortQuestionCategoryNo: Int? = null
)