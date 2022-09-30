package com.ciptakerjaarunika.kerjaloka.model.Data

data class TestResponse(
    val code: Int,
    val data: List<TestJob>
)

data class TestJob(
    val testNo: Long,
    val testName: String,
    val questions: List<question>
)

data class question(
    val questionNo: Long,
    val question: String,
    val type: Int,
)
