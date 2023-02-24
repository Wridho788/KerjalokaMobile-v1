package com.ciptakerjaarunika.kerjaloka.viewmodel.NotificationPage.Model

data class notifResponse(
    val code: Int, val data: List<CompanyNotificationModel>, val errorCode: Int, val message: String
)

data class CompanyNotificationModel(

    val read: Boolean,
    val createdOn: String,
    val notifStatus: Long,
    val notificationNo: Long,
    val photo: String? = null,
    val message: String,
    val url: String

)