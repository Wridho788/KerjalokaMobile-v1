package com.ciptakerjaarunika.kerjaloka.model.User

import java.util.*

data class LoginRequest(
    val email : String,
    val password : String,
)

data class LoginResponse(
    val code : Int,
    val message : String,
    val userToken :  String,
    val userNo :  Long,
    val userFUllname :  String,
    val userRole :  Int,
    val suspended :  Boolean?,
    val photo :  Boolean,
    val deactivated :  Boolean,
    val dataComplete :  Boolean,
    val ownerStatus :  Boolean?,
    val authorized :  Boolean?,
    val notice :  Long?,
    val privilege : List<RolePrevileges>?
)
data class LoginResponseData(
    val companyNo : Long,
    val jobPosition : String,
    val jobNo : Long,
    val logo : String,
    val status : String,
    val link : String,
)
