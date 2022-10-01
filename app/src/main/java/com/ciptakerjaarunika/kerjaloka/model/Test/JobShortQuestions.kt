package com.ciptakerjaarunika.kerjaloka.model.Test

data class JobShortQuestions(
    val shortQuestionNo : Long?,
    val questionType : Int,
    val shortQuestion : String,
    var answer : String,
    var choice : List<ShortQuestionChoices>
)
data class ShortQuestionChoices(
    var shortQuestionChoiceNo : Int,
    var choice : String,
    var isSelected : Boolean
)
