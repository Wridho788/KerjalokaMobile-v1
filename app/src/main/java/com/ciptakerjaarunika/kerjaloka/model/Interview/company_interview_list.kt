package com.ciptakerjaarunika.kerjaloka.model.Interview

data class conmpany_interview_list_api(
    val code : Int,
    val errorCode : Int,
    val message : String,
    val data :  List<company_interview_list>,
)
data class company_interview_list(
    val jobPosition : String,
    val jobNo : Long,
    val interviewer :  List<interview_data>,
)
data class interview_data(
    val jobseekerName : String,
    val photo : String,
    val userNo : Long,
)
