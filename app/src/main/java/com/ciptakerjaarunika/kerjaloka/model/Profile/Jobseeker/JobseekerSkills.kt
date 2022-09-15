package com.ciptakerjaarunika.kerjaloka.model.Profile

import com.ciptakerjaarunika.kerjaloka.model.Data.*
import com.ciptakerjaarunika.kerjaloka.model.User.Jobseeker
import com.ciptakerjaarunika.kerjaloka.model.User.JobseekerAdditional
import com.ciptakerjaarunika.kerjaloka.model.User.User

data class JobseekerSkillsResponse(
    val code : Int,
    val data : List<JobseekerSkills>,
)
data class JobseekerSkills(
    val jobseekerNo: Long,
    val scale : Int,
    val skillName : String,
    val skillNo : Int,
)

