package com.ciptakerjaarunika.kerjaloka.model.Profile

import com.ciptakerjaarunika.kerjaloka.model.Data.*
import com.ciptakerjaarunika.kerjaloka.model.User.Jobseeker
import com.ciptakerjaarunika.kerjaloka.model.User.JobseekerAdditional
import com.ciptakerjaarunika.kerjaloka.model.User.User

data class JobseekerProfileResponse(
    val code : Int,
    val data : JobseekerProfile,
    val message : String?
)

data class JobseekerProfile(
    val additionals : JobseekerAdditional,
    val city: City,
    val country: Country,
    val jobseeker: Jobseeker,
    val marital: Marital,
    val province: Province,
    val religion: Religion,
    val resident: Resident,
    val users : User
)
