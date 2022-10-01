package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model


import com.ciptakerjaarunika.kerjaloka.model.Job.JobLocation

data class addJobResponse(
    val code: String,
    val message: String,

    )

data class addJobRequest(
    val position: String? ="",
    val location: List<JobLocation>,
    val jobType: Int? =0,
    val minSalary: Int? = 0,
    val maxSalary: Int? = 0,
    val jobSkills: List<JobSkillRequest>,
    val jobTitle: List<JobTitleRequest>,
    val jobField: Int? = 0,
    val jobRole: Int? = 0,
    val minExperience: Int? = 0,
    val jobExperienceLevelNo: Int? = 0,
    val jobDescription: String? = "",
    val jobTests: List<JobTest>?,
    val jobShortQuestion: List<JobShortQuestionDto>?,
    val autoReject: Boolean?
)

data class JobSkillRequest(
    val jobSkillNo: Long? = 0,
    val jobNo: Long? = 0,
    val skillNo: Long? = 0,
)

data class JobTitleRequest(
    val jobTitleNo: Long? = 0,
    val jobNo: Long? = 0,
    val titleNo: Long? = 0,
)

data class JobShortQuestionDto(
    val jobShortQuestionNo: Long? = 0,
    val jobNo: Long?=0,
    val shortQuestionNo: Long? = 0,
    val mustHave: Boolean? = false,
    val choice: List<ShortChoice>
)

data class ShortChoice(
    val selected: Boolean? = false,
    val shortQuestionChoiceNo: Int? = 0,
)

