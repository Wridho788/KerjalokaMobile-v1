package com.ciptakerjaarunika.kerjaloka.model.User

import java.util.*

data class Company(
    val companyNo : Long?,
    val userNo : Long,
    val companyName : String,
    val companyNickname : String,
    val authorized : String,
    val authorizedUserNo : Long?,
    val accountManager : Long?,
)
