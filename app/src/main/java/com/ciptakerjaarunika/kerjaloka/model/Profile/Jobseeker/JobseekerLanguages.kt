package com.ciptakerjaarunika.kerjaloka.model.Profile

import com.ciptakerjaarunika.kerjaloka.model.Data.*
import com.ciptakerjaarunika.kerjaloka.model.User.Jobseeker
import com.ciptakerjaarunika.kerjaloka.model.User.JobseekerAdditional
import com.ciptakerjaarunika.kerjaloka.model.User.User

data class JobseekerLanguagesResponse(
    val code : Int,
    val data : List<JobseekerLanguages>,
)
data class JobseekerLanguages(
    val jobseekerNo: Long,
    val languageName : String,
    val language : Int,
    val languageSpokenScale : Int,
    val languageWrittenScale : Int
)

