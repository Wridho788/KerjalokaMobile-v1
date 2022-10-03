package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model


import com.ciptakerjaarunika.kerjaloka.model.Job.JobLocation
import java.math.BigDecimal

data class addJobResponse(
    val code: String,
    val message: String,
    )

data class getJobResponse(
    val code: String,
    val data: List<ResponseJobs>
)

data class addJobRequest(
    var Position: String? = "",
    var Location: List<JobLocation>,
    var JobType: Int? = 0,
    var MinSalary: BigDecimal?,
    var MaxSalary: BigDecimal?,
    var JobSkills: List<JobSkillRequest>,
    var JobTitles: List<JobTitleRequest>,
    var JobField: Int? = 0,
    var JobRole: Int? = 0,
    var MinExperience: Int? = 0,
    var JobExperienceLevelNo: Int? = 0,
    var JobDescription: String? = "",
    var JobTest: List<JobTest>?,
    var JobShortQuestion: List<JobShortQuestionDto>?,
    var AutoReject: Boolean?,
    var createdOn: String? = "",
    var expired: String? = "",
    var createdBy: String? = "",
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
    var MustHave: Boolean? = false,
    var choice: List<ShortChoice>
)

data class ShortChoice(
    var selected: Boolean? = false,
    var shortQuestionChoiceNo: Int? = 0,
)

