package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionKomentar.Model

data class CommentResponse(
    val code: Int,
    val errorCode: Int,
    val message: String,
    val data: String,
)

data class CommentModel(
    val applicantCommentNo: Long,
    val jobseekerNo:Long,
    val companyNo: Long,
    val commentByUserNo: Long,
    val comment: String,
    val commentAt: String?
)

data class send_comment(
    val jobseekerNo:Long,
    val comment: String,
)
