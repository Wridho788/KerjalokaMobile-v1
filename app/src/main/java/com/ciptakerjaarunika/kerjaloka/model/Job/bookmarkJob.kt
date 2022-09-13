package com.ciptakerjaarunika.kerjaloka.model.Job

data class BookmarkJob(
    val JobNo :Long,
    val UserNo : Long
)

data class BookmarkResponse(
    val code : Int,
    val Message : String
)

