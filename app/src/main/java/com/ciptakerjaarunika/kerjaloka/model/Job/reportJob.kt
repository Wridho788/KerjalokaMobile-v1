package com.ciptakerjaarunika.kerjaloka.model.Job

data class ReportJobRequest(
    val JobNo : Long,
    val ReportByUserNo : Long,
    val ReportMessage : String
)

data class ReportJobResponse(
    val code : Int,
    val Message : String
)

