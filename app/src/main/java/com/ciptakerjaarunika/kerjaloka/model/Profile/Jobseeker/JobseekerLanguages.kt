package com.ciptakerjaarunika.kerjaloka.model.Profile

data class JobseekerLanguagesResponse(
    val code: Int,
    val data: List<JobseekerLanguages>,
)

data class JobseekerLanguages(
    val jobseekerNo: Long,
    val languageName: String,
    val languageNo: Int,
    val languageSpokenScale: Int,
    val languageWrittenScale: Int
)

