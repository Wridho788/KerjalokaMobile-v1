package com.ciptakerjaarunika.kerjaloka.model.Job

data class ApplyJobRequest(
    val message : String,
    val tests : List<ApplicationTests>,
    val shortQuestionAnswer : List<JobShortAnswer>
)

