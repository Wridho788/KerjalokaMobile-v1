package com.ciptakerjaarunika.kerjaloka.model.User

import java.time.LocalDateTime
import java.util.*

data class Jobseeker(
    val jobseekerNo : Long?,
    val userNo : Long?,
    val jobseekerName : String,
    val jobseekerGender : Char,
    val dateOfBirth : String,
    val noExperience : Boolean,
)
