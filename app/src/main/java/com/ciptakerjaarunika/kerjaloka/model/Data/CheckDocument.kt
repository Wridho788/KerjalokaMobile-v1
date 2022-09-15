package com.ciptakerjaarunika.kerjaloka.model.Data

data class CheckDocument(
    val address : String,
    val checkDocumentNo : String,
    val createdBy : Long,
    val createdOn : String,
    val documentFileName : String,
    val documentStatus : Int,
    val documentType : Int,
    val updatedBy : Long,
    val updatedOn : String,
    val userNo : Long,
)
