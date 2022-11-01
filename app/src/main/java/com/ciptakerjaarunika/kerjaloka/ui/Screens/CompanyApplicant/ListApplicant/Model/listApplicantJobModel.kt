package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.Model

import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.*
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.jobField
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.jobTitle
import java.math.BigDecimal

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
    val jobDescription: String,
    val jobType: jobType,
    val jobExperienceLevel: JobExperienceLevel,
    val jobCity: List<String>,
    val jobSalaryMin: BigDecimal?,
    val jobSalaryMax: BigDecimal?,
    val jobField: jobField,
    val jobRole: JobRole,
    val jobMinExperience: Int?,
    val jobSkill: List<JobSkill>,
    val jobTitle: List<jobTitle>,
    val createdOn: String,
    val updatedOn: String?,
    val createdBy: String?,
    var publish: Boolean,
    val jobShortQuestion: List<JobShortQuestion>,
    val jobTest: List<JobTest>,
    val expired: String?,
    val takedown: Boolean?,
    val jobAdditionalInfo: String?
    )

data class jobType(
    val jobTypeNo: Int,
    val jobTypeName : String,
)


data class jobField(val fieldName: String)

