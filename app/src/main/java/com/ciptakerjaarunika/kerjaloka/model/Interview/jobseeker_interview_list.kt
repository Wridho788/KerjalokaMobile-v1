package com.ciptakerjaarunika.kerjaloka.model.Interview

data class jobseeker_interview_list_api(
    val code : Int,
    val errorCode : Int,
    val message : String,
    val data :  List<jobseeker_interview_list>,
)
data class jobseeker_interview_list(
    var jobPosition : String,
    val jobNo : Long,
    val companyNo :  Long,
    var companyName :  String,
    val photo :  String,
)
