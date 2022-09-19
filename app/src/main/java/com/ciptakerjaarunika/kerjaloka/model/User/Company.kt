package com.ciptakerjaarunika.kerjaloka.model.User

import java.util.*

data class Company(
    val userNo : Long,
    val logo : String,
    val companyName : String,
    val companyNickname : String,
    val authorized : String,
    val authorizedUserNo : Long?,
    val accountManager : Long?,
)
