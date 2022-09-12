package com.ciptakerjaarunika.kerjaloka.model.Interview

import com.ciptakerjaarunika.kerjaloka.model.Job.jobHomeListData
import java.util.*

data class jobseeker_interview_list_api(
    val code : Int,
    val errorCode : Int,
    val message : String,
    val data :  List<jobseeker_interview_list>,
)
data class jobseeker_interview_list(
    val jobPosition : String,
    val jobNo : Long,
    val companyNo :  Long,
    val companyName :  String,
    val photo :  String,
)
