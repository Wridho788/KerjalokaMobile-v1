package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.Model

data class listApplicantJobModel(
    val jobNo: Int,
    val jobPosition: String,
    val uploadedAt: String,
    var status: Boolean
)
