package com.ciptakerjaarunika.kerjaloka.model.User

import java.util.*

data class User(
    var userNo: Long,
    var email: String,
    var phone: String,
    var username: String,
    var userFullname: String,
    var roleNo: Int,
    var photo: String?,
    var suspended: Boolean,
    var deactivated: Boolean,
    var dataComplete: Boolean,
    var ownerStatus: Boolean?,
    var jobseekers: Jobseeker?,
    var authorized: Boolean?,
    var notice: Long?,
    var jobseekerAdditional: JobseekerAdditional?,
    var company: Company?,
    var companyAdditional: CompanyAdditional?,
    var rolePrivileges: List<RolePrevileges>?
)
