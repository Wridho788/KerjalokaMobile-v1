package com.ciptakerjaarunika.kerjaloka.model.Profile

data class JobseekerSkillsResponse(
    val code: Int,
    val data: List<JobseekerSkills>,
)

data class JobseekerSkills(
    val jobseekerNo: Long,
    val scale: Int,
    val skillName: String,
    val skillNo: Int,
)

