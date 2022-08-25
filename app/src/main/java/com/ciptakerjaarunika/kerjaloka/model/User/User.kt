package com.ciptakerjaarunika.kerjaloka.model.User

import java.util.*

data class User(
    val userNo : Long,
    val email : String,
    val phone : String,
    val username : String,
    val userFullname : String,
    val roleNo : Int,
    val suspended : Boolean,
    val deactivated : Boolean,
    val jobseekers : Jobseeker,
    val jobseekerAdditional : JobseekerAdditional,
    val company : Company,
    val companyAdditional: CompanyAdditional,
    val rolePrevileges: RolePrevileges
)
