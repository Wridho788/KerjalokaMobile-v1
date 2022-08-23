package com.ciptakerjaarunika.kerjaloka.model

import java.util.*

data class ResponseResult(
    val code : Int,
    val errorCode : Int,
    val message : String,
    val data : Object?,
)
