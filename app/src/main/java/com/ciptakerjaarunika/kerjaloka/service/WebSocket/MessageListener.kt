import okhttp3.Response
import okhttp3.WebSocket

interface MessageListener {
    fun  onOpen (webSocket: WebSocket, response: Response?)
    fun  onConnectSuccess () // successfully connected
    fun  onConnectFailed () // connection failed
    fun  onClose ()
    fun onMessage(text: String?)
}