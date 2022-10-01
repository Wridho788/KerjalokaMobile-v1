package com.ciptakerjaarunika.kerjaloka.model.Data

data class Field(
    val fieldName : String,
    val fieldNo : Int,
)
data class FieldFilter(
    val fieldName : String,
    val fieldNo : Int,
    var checked: Boolean
)
