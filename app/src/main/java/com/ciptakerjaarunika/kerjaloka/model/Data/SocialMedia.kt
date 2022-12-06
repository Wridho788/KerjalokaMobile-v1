package com.ciptakerjaarunika.kerjaloka.model.Data

data class CheckSocialMediaResponse(
    val code: Int,
    val google: Boolean,
//    val facebook: Boolean
)

data class AddSocialMediaResponse(
    val code: Int,
    val message: String,
    val data: socialMedia
)

data class socialMedia (
    val socialMediaConnectionNo: Int?,
    val userNo : Long,
    val socialMediaNo: Int,
    val accessToken: String
        )
