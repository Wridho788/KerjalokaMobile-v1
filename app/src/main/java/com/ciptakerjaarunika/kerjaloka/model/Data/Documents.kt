package com.ciptakerjaarunika.kerjaloka.model.Data

import okhttp3.MultipartBody

data class Documents(
    var documentFileName : String,
    val documentName : String,
    val documentNo : Int?,
    val documentTypeNo : Int?,
    val userNo : Long,
    var documentFile : MultipartBody.Part?
)
