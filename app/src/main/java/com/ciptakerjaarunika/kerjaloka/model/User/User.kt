package com.ciptakerjaarunika.kerjaloka.model.User

import java.util.*

data class User(
    var userNo: Long,
    var email: String,
    var phone: String,
    var username: String,
    var emailHasVerified: Boolean?,
    var userFullname: String,
    var roleNo: Int,
    var photo: String?,
    var suspended: Boolean,
    var deactivated: Boolean,
    var dataComplete: Boolean,
    var ownerStatus: Boolean?,
    var jobseekers: Jobseeker?,
    var authorized: Boolean?,
    var isDeleted: Boolean?,
    var isNewsletter: Boolean?,
    var lastChangeUsername: String?,
    var notice: Long?,
    var jobseekerAdditional: JobseekerAdditional?,
    var company: Company?,
    var companyAdditional: CompanyAdditional?,
    var rolePrivileges: List<RolePrevileges>?
)
