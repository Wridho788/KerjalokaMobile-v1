package com.ciptakerjaarunika.kerjaloka.model.User

import java.util.*

data class user_model(
    val code : Int,
    val errorCode : Int,
    val message : String,
    val data :  userData,
)
data class userData(
    val userNo : Long,
    val username : String,
    val userfullname : Long,
    val logo : String,
    val status : String,
    val link : String,
)