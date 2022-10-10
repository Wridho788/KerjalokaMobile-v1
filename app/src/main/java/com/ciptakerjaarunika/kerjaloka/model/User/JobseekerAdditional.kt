package com.ciptakerjaarunika.kerjaloka.model.User

import java.util.*

data class JobseekerAdditional(
    val jobseekerNo : Long,
    val jobseekerAbout : String,
    val jobseekerCurrentAddress : String,
    val jobseekerCityNo : Int?,
    val jobseekerProvinceNo : Int?,
    val jobseekerCountryNo : Int?,
    val postalCode : String,
    val placeOfBirth : String,
    val photo : String,
    val maritalNo : Int?,
    val ethnics : String,
    val religionNo : Int?,
    val residentNo : Int?,
    val expectedSalary : Int?,
    val ktp : String,
    val ktpImage : String,
    val vaccinated : Boolean,
    val telegramId : String,
    val instagramId : String,
)
