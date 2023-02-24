package com.ciptakerjaarunika.kerjaloka.model.Interview

data class returnUploadChatPhotoApi(
    val code : Int,
    val errorCode : Int,
    val message : String,
    val data :  returnUploadFile
)

data class returnUploadFile(
    val fileName : String,
    val resultFileName : String,
)