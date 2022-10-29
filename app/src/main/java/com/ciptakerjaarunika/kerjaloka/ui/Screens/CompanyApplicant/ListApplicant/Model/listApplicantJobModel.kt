package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.Model

import com.anychart.scales.DateTime
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
    val jobNo: String,
    val jobPosition: String,
    val jobDescription: String,
    val jobType: jobType,
    val jobExperienceLevel: JobExperienceLevel,
    val jobCity: jobCity,
    val jobSalaryMin: BigDecimal?,
    val jobSalaryMax: BigDecimal?,
    val jobField: jobField,
    val jobRole: JobRole,
    val jobMinExperience: Int?,
    val jobSkill: JobSkill,
    val jobTitle: jobTitle,
    val createdOn: String,
    val updatedOn: DateTime?,
    val createdBy: String?,
    var publish: Boolean,
    val jobShortQuestion: JobShortQuestion,
    val jobTest: JobTest,
    val expired: DateTime?,
    val takedown: Boolean?,
    val jobAdditionalInfo: String?
    )

data class jobType(
    val jobTypeNo: Int,
    val jobTypeName : String,
)

data class jobCity(
    val cityNo: Int
)

data class jobField(val fieldName: String)

