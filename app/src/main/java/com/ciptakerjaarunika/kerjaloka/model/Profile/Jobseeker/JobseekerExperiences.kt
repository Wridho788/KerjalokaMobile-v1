package com.ciptakerjaarunika.kerjaloka.model.Profile

import com.ciptakerjaarunika.kerjaloka.model.Data.*
import com.ciptakerjaarunika.kerjaloka.model.User.Jobseeker
import com.ciptakerjaarunika.kerjaloka.model.User.JobseekerAdditional
import com.ciptakerjaarunika.kerjaloka.model.User.User
import java.math.BigDecimal

data class JobseekerExperiencesResponse(
    val code : Int,
    val data : List<JobseekerExperiences>,
)
data class JobseekerExperiences(
    val jobseekerExperienceNo: Long?,
    val experienceBeginAt: String,
    val experienceCityName : String,
    val experienceCityNo : Int,
    val experienceCompanyName : Int,
    val experienceCompanyNo : Int,
    val experienceCountry : String,
    val experienceDescription : String,
    val experienceEndedAt : String,
    val experienceJobTypeName : String,
    val experienceJobTypeNo : Int,
    val experienceNo :Int,
    val experiencePosition : String,
    val experienceProvinceName : String,
    val experienceSalary : BigDecimal?,
    val jobseekerNo : Long
)

