package com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model

import org.ocpsoft.prettytime.PrettyTime

data class rjob_model(
    val code : Int,
    val errorCode : Int,
    val message : String,
    val data :  List<rJobModel>,
)
data class rJobModel(
    val companyNo: Long,
    val jobNo: Long,
    val jobPosition: String,
    val jobLocation: String,
//    val timeUploadApplicant: String,
    val logo: String,
    val status: String,
    val link: String,
    val CompanyName : String,
//    val CreatedOn : PrettyTime
)
data class rJobDetailModel(

)
