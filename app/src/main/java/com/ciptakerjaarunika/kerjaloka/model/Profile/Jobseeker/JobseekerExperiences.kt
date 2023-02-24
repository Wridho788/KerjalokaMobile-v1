package com.ciptakerjaarunika.kerjaloka.model.Profile

import java.math.BigDecimal

data class JobseekerExperiencesResponse(
    val code : Int,
    val data : List<JobseekerExperiences>,
)
data class JobseekerExperiences(
    val jobseekerExperienceNo: Long?,
    val experienceBeginAt: String,
    val experienceCityName : String,
    var experienceCityNo : Int,
    val experienceCompanyName : String,
    val experienceCompanyNo : Long,
    val experienceCountry : String,
    val experienceDescription : String,
    val experienceEndedAt : String?,
    val experienceJobTypeName : String,
    val experienceJobTypeNo : Int,
    val experienceNo :Long,
    val experiencePosition : String,
    val experienceProvinceName : String,
    val experienceSalary : BigDecimal?,
    val jobseekerNo : Long
)

data class JobseekerExperienceRequest(
    val jobseekerExperienceNo: Long?,
    val jobseekerNo: Long?,
    var experienceCityNo : Int?,
    val experienceCompanyName : String?,
    val experienceCompanyNo : Long?,
    val experienceDescription : String?,
    val experienceEndedAt : String?,
    val experienceBeginAt : String?,
    var jobTypeNo : Int?,
    val experiencePosition : String?,
    val experienceSalary : BigDecimal?,
)

