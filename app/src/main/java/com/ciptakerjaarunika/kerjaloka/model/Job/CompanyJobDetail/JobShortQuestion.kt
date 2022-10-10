package com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail

data class JobShortQuestion(
    var shortQuestionNo: Long,
    var shortQuestion: String,
    var questionType : Int,
    var mustHave : Boolean?,
    var choice : List<JobShortQuestionChoice>
)

data class JobShortQuestionChoice(
    var shortQuestionChoiceNo : Int?,
    var choice : String,
    var selected : Boolean
)