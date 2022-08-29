package com.ciptakerjaarunika.kerjaloka.service.WebSocket

import MessageListener
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_model
import okhttp3.*
import okio.ByteString
import okio.ByteString.Companion.decodeHex


class WebSocketService : MessageListener {
    private var _webSocket: WebSocket? = null;

    override fun onConnectSuccess() {
        println( " Connected successfully \n " )
    }

    override fun onConnectFailed() {
        println( " Connection failed \n " )
    }

    override fun onClose() {
        WebSocketManager.close()
        println( " Closed successfully \n " )
    }

    override fun onMessage(text: String?) {
        println( " Receive message: $text \n " )
    }

    override fun getMessage(ListMessage: List<chat_model>) {
        println( ListMessage.toString() )
    }

    open fun startWebsocket() {
       WebSocketManager.init(config().portAddress+"ws/chat", this)
       WebSocketManager.connect()
    }

    companion object {
        private const val CLOSE_STATUS = 1000
    }

    override fun onOpen(webSocket: WebSocket, response: Response?) {
        TODO("Not yet implemented")
    }
}