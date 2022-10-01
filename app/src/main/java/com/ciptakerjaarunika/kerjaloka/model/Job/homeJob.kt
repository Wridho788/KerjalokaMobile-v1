package com.ciptakerjaarunika.kerjaloka.model.Job

data class homejob_model(
    val code : Int,
    val errorCode : Int,
    val message : String,
    val data :  List<jobHomeListData>,
)
data class jobHomeListData(
    val companyNo : Long,
    val jobPosition : String,
    val jobNo : Long,
    val logo : String,
    val status : String,
    val link : String,
)
