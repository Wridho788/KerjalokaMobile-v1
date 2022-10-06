package com.ciptakerjaarunika.kerjaloka.enum

enum class QuestionType(val value: Int, name: String) {
    SubQuestion(1, "Sub Question"),
    MultipleChoice(2, "Pilihan Ganda"),
    MultipleAnswer(3, "Pilihan Ganda"),
    Essay(4, "Essay"),
    Programming(5, "Programming"),
    Attachment(6, "Attachment"),
    PapiKostick(7, "PapiKostick"),
}