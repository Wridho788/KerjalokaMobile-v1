package com.ciptakerjaarunika.kerjaloka.model.User

data class Jobseeker(
    val jobseekerNo: Long?,
    val userNo: Long?,
    val jobseekerName: String,
    val jobseekerGender: Char,
    val dateOfBirth: String,
    val noExperience: Boolean,
)
