package com.ciptakerjaarunika.kerjaloka.model.Test

data class ShortQuestions(
    val shortQuestionNo : Long?,
    val companyNo : Long,
    val shortQuestion : String,
    val enabled : Boolean,
    val createdBy : Long,
    val createdOn : String,
    val questionType : Int,
    val updatedOn : String,
    val updatedBy : Long,
    val shortQuestionCategoryNo : Int?,
)
