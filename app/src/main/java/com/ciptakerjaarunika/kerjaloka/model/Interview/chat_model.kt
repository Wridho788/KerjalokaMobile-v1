package com.ciptakerjaarunika.kerjaloka.model.Interview

import java.util.*
data class chat_data(val sections :List<chat_model>)
data class chat_model(
    val sectionName: String,
    val sectionNo: Int,
    val notRead: Int,
    val jobNo: Long?,
    val messages : List<Messages>,
    val receiver : List<Long>)

data class Messages(val message : String,
                    val createdBy: Long,
                    val createdOn: Date,
                    val hasRemove: Boolean,
                    val reader : List<Reader>){}

data class Reader(val chatMessageNo : Int,
                  val readBy: Long,
                  val readOn: Date,
                  val hasRemove: Boolean){}