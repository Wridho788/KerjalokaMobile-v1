package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage

import java.util.*

data class chat_model(val SectionName: String,val NotRead: Int, val Messages : List<Messages>){}

data class Messages(val Message : String, val CreatedBy: Long, val CreatedOn: Date,val HasRemove: Boolean,val Reader : List<Reader>){}

data class Reader(val ChatMessageNo : Int, val ReadBy: Long, val ReadOn: Date,  val HasRemove: Boolean){}