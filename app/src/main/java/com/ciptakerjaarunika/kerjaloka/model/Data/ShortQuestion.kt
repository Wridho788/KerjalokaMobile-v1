package com.ciptakerjaarunika.kerjaloka.model.Data

data class ShortQuestionResponse(
    val code: String,
    val data: List<ShortQuestion>
)

data class ShortQuestion(
    val shortQuestionNo: Long,
    val companyNo: Long,
    val questionType: Int,
    val shortQuestion: String,
    val enabled: Boolean,
    val createdBy: String,
    val createdOn: String,
    val choice: List<choice>,
    val shortQuestionCategoryNo: Long,
    val categoryName: String,
)

data class choice(
    val shortQuestionChoiceNo: Int,
    val shortQuestionNo: Int,
    val choice: String,
    val isAnswer: Boolean
)

data class ShortQuestionCategoryResponse(
    val code: String,
    val data: List<ShortQuestionCategory>
)

data class ShortQuestionCategory(
    val shortQuestionCategoryNo: Long,
    val categoryName: String
)