package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.Bottomsheet.MoreAction.Model

data class BookmarkResponse(
    val code: Int,
    val errorCode: Int,
    val message: String,
    val data: BookmarkModel,
)

data class BookmarkModel(
    val bookmarkApplicationNo: Long?,
    val companyNo: Long,
    val applicationNo: Long,
    val bookmarkByUserNo: Long,
    val bookmarkOn: String?
)

data class send_bookmark(
    val jobNo: Long,
    val jobseekerNo: Long,
)
