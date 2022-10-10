package com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail

import com.ciptakerjaarunika.kerjaloka.model.Data.JobType
import com.ciptakerjaarunika.kerjaloka.model.Job.Company

data class CompanyJobDetailResponse(
    val data : List<CompanyJobDetail>
)

data class CompanyJobDetail(
    var createdBy: String?,
    var createdOn: String?,
    var expired: String?,
    var jobAdditionalDescription: List<Any>,
    var jobDescription: String?,
    var jobExperienceLevel: JobExperienceLevel?,
    var jobField: JobField?,
    var jobLocation: List<JobLocation>,
    var jobMinExperience: Int?,
    var jobNo: String?,
    var jobPosition: String?,
    var jobRole: JobRole?,
    var jobSalaryMax: Int?,
    var jobSalaryMin: Int?,
    var jobShortQuestion: List<JobShortQuestion>,
    var jobSkills: List<JobSkill>,
    var jobTest: List<JobTest>,
    var jobTitle: List<JobTitle>,
    var jobType: JobType?,
    var publish: Boolean?,
    var takedown: Boolean?,
    var updatedOn: String?,
)