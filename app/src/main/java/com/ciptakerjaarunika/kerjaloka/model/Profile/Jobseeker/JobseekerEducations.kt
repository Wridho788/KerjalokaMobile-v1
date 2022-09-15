package com.ciptakerjaarunika.kerjaloka.model.Profile


data class JobseekerEducationsResponse(
    val code : Int,
    val data : List<JobseekerEducations>,
)
data class JobseekerEducations(
    val educationBeginAt: String,
    val educationCityName : String,
    val educationCityNo : Int,
    val educationCountry : String,
    val educationDescription : String,
    val educationEndedAt : String,
    val educationMajorName : String,
    val educationMajorNo : Int,
    val experienceNo :Int,
    val educationProvinceName : String,
    val educationSchool : String,
    val educationTitleName : String,
    val educationTitleNo : Int,
    val gpa:Int,
    val jobseekerEducationNo : Int,
    val jobseekerNo : Long
)

