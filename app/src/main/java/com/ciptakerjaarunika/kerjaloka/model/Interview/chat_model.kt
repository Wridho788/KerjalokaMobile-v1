package com.ciptakerjaarunika.kerjaloka.model.Interview

data class chat_data(var sections: List<chat_model>)
data class chat_model(
    var sectionName: String,
    var logo: String?,
    var sectionNo: Int,
    var notRead: Int,
    var jobNo: Long?,
    var messages: List<Messages>,
    var receiver: List<Long>
)

data class Messages(
    var chatMessageNo: Int,
    var message: String,
    var createdBy: Long,
    var createdOn: String,
    var hasRemove: Boolean,
    var messageType: Int,
    var fileName: String,
    var reader: List<Reader>
)

data class Reader(
    var chatMessageNo: Int,
    var readBy: Long,
    var readOn: String,
    var hasRemove: Boolean
)

enum class MessageType(val type: Int) {
    NormalMessage(1),
    VoiceMessage(2),
    ImageMessage(3),
    FileMessage(4)
}