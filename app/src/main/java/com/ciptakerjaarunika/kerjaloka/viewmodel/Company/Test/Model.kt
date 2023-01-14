package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Test

data class testResponse(
    val code: Int,
    val data: List<Test>
)

data class Test (
    val testNo: Long,
    val testName: String,
    val testDuration: Long,
    val testPeriod: Long,
    val maxScore: Long,
    val testEnabled: Boolean,
    val testHint: String,
    val isPublic: Boolean,
    val isSpecial: Boolean,
    val createdBy: String,
    val createdOn: String,
    val questions: List<Question>,
    val isTakedown: Boolean,
    val isOwn: Boolean,
    val testLink: Any? = null,
    val testMarketNo: Any? = null,
    val price: Any? = null
)

data class Question (
    val questionNo: Long,
    val question: String,
    val type: Long,
    val maxScore: Long,
    val subQuestion: Long
)
