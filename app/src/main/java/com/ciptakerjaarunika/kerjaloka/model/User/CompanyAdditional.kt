package com.ciptakerjaarunika.kerjaloka.model.User

import java.time.LocalDateTime
import java.util.*

data class CompanyAdditional(
    val companyNo : Long,
    val companyAddress : String,
    val companyCityNo : Int?,
    val companyProvinceNo : Int?,
    val companyCountryNo : Int?,
    val companyCeo : String,
    val ktp : String,
    val foundedAt : LocalDateTime?,
    val fieldNo : Int?,
    val sizeNo : Byte?,
    val logo : String,
    val companyDescription : String,
    val businessLicenseNumber : String,
    val companyTypeNo : Int,
)
