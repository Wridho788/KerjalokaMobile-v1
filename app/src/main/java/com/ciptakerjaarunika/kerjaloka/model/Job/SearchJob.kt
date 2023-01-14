package com.ciptakerjaarunika.kerjaloka.model.Job

import com.ciptakerjaarunika.kerjaloka.model.Data.JobType
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.model.*
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.HomePage.Model.jobLocation
import java.math.BigDecimal

data class SearchJobResponse(
    val code : Int,
    val Message: String,
    val data: List<SearchJobModel>
)

data class SearchJobModel(
    val jobNo : String,
    val jobPosition : String,
    val jobDescription : String,
    val jobType : JobType?,
    val jobExperienceLevel: JobExperienceLevel,
    val jobSalaryMax : BigDecimal?,
    val jobSalaryMin : BigDecimal?,
    val jobField : JobField?,
    val jobRole : JobRole?,
    val link : String,
    val jobMinExperience: Int?,
    val jobSkill : List<JobSkill>,
    val jobTitle : List<JobTitle>,
    val createdOn : String,
    val createdBy : String,
    val publish : Boolean,
    val company: Company,
    var bookmarked : Boolean,
    val jobLocation: List<jobLocation>,
    val takedown: Boolean,
    val applied : Any?
)

data class RelatedModel(
    val jobNo : String,
    val jobPosition : String,
    val jobDescription : String,
    val jobType : JobType?,
    val jobExperienceLevel: JobExperienceLevel,
    val jobSalaryMax : BigDecimal?,
    val jobSalaryMin : BigDecimal?,
    val jobField : String?,
    val jobRole : JobRole?,
    val link : String,
    val jobMinExperience: Int?,
    val jobSkill : List<JobSkill>,
    val jobTitle : List<JobTitle>,
    val createdOn : String,
    val createdBy : String,
    val publish : Boolean,
    val company: Company,
    var bookmarked : Boolean,
    val jobLocation: List<jobLocation>,
    val takedown: Boolean,
    val applied : Any?
)
