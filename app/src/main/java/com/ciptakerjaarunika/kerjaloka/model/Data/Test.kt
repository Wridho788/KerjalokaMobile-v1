package com.ciptakerjaarunika.kerjaloka.model.Data

data class TestResponse(
    val code: Int,
    val data: List<TestJob>
)

data class TestJob(
    val testNo: Long,
    val testName: String,
    val question: List<questionList>
)

data class questionList(
    val question: List<question>
)


data class question(
    val question: String,
    val questionTypeNo: Int,
)
