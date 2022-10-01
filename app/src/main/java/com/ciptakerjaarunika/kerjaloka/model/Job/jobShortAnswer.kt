package com.ciptakerjaarunika.kerjaloka.model.Job

data class JobShortAnswer(
    val shortQuestionNo : Long?,
    val answer : String,
    val choice : List<JobShortAnswerChoices>,
)
data class JobShortAnswerChoices(
    val shortQuestionChoiceNo : Long?,
    val isSelected : Boolean,
    val choice : String,
)
