package com.ciptakerjaarunika.kerjaloka.model.User

data class LoginRequest(
    val email: String, val password: String, val deviceToken: String
)

data class GoogleLoginRequest(
    val token: String,
    val expiredOn: String,
    val hash: String,
    val role: Int = 4,
    val scode: String = "",
    val deviceToken: String?
)

data class LoginResponse(
    val code: String,
    val message: String,
    val userToken: String? = null,
    val userNo: Long? = null,
    val userFullname: String? = null,
    val userRole: Int? = null,
    val suspended: Boolean? = null,
    val photo: String? = null,
    val deactivated: Boolean? = null,
    val dataComplete: Boolean? = null,
    val ownerStatus: Boolean? = null,
    val authorized: Boolean? = null,
    val notice: Long? = null,
    val privilege: List<RolePrevileges>? = null
)

data class LoginResponseData(
    val companyNo: Long,
    val jobPosition: String,
    val jobNo: Long,
    val logo: String,
    val status: String,
    val link: String,
)

data class CheckLoginResponse(
    val user: Any
)
