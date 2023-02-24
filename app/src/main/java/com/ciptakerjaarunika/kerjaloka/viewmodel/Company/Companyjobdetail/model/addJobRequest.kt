package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.model


import com.ciptakerjaarunika.kerjaloka.model.Data.JobType
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobLocation

data class addJobResponse(
    val code: String,
    val message: String,
)

data class getJobResponse(
    val code: String, val data: List<ResponseCompanyJobs>
)

data class addJobRequest(
    var JobNo: Long?,
    var Position: String? = "",
    var Location: List<JobLocation>,
    var JobType: Int?,
    var MinSalary: Int?,
    var MaxSalary: Int?,
    var JobSkills: List<com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobSkill>,
    var JobTitles: List<com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobTitle>,
    var JobField: Int?,
    var JobRole: Int?,
    var MinExperience: Int?,
    var JobExperienceLevelNo: Int?,
    var JobDescription: String?,
    var JobTest: List<com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobTest>,
    var JobShortQuestion: List<com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobShortQuestion>,
    var AutoReject: Boolean? = false,
)

data class JobSkillRequest(
    var jobSkillNo: Long? = 0,
    var jobNo: Long? = 0,
    var skillNo: Long? = 0,
)

data class JobTitleRequest(
    var jobTitleNo: Long? = 0,
    var jobNo: Long? = 0,
    var titleNo: Long? = 0,
)

data class JobShortQuestionDto(
    var JobShortQuestionNo: Long? = 0,
    var JobNo: Long? = 0,
    var ShortQuestionNo: Long? = 0,
    var MustHave: Boolean? = true,
    var choice: List<ShortChoice>
)

data class ShortChoice(
    var selected: Boolean? = false,
    var shortQuestionChoiceNo: Int? = 0,
)

data class ResponseCompanyJobs(
    val jobNo: String,
    val jobPosition: String,
    val jobDescription: String,
    val link: String,
    val jobType: JobType?,
    val jobExperienceLevel: JobExperienceLevel?,
    val jobCity: String,
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


data class JobTestRequest(
    val jobTestNo: Long,
    val jobNo: Long,
    val testNo: Long,
)