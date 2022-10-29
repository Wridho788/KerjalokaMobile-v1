package com.ciptakerjaarunika.kerjaloka.model.User

data class LoginRequest(
    val email : String,
    val password : String,
    val deviceToken : String
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
    var code: String,
    var message: String,
    val userToken: String,
    val userNo: Long,
    val userFullname: String,
    val userRole: Int,
    val suspended: Boolean,
    val photo: String?,
    val deactivated: Boolean,
    val dataComplete: Boolean,
    val ownerStatus: Boolean?,
    val authorized: Boolean?,
    val notice: Long?,
    val privilege: List<RolePrevileges>?
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
