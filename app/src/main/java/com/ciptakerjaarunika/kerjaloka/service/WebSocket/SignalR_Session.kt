//package com.ciptakerjaarunika.kerjaloka.service.WebSocket
//
//import android.content.Context
//import android.widget.Toast
//import androidx.test.core.app.ApplicationProvider.getApplicationContext
//import okhttp3.*
//import java.util.concurrent.ExecutionException
//
//
//class SignalR_Session {
//    private fun startSignalR() {
//        loadPlatformComponent(AndroidPlatformComponent())
//        val credentials: Credentials = object : Credentials() {
//            fun prepareRequest(request: Request) {
//                request.addHeader("User-Name", "BNK")
//            }
//        }
//        val serverUrl = "http://192.168.1.100"
//        mHubConnection = HubConnection(serverUrl)
//        mHubConnection.setCredentials(credentials)
//        val SERVER_HUB_CHAT = "ChatHub"
//        mHubProxy = mHubConnection.createHubProxy(SERVER_HUB_CHAT)
//        val clientTransport: ClientTransport = ServerSentEventsTransport(mHubConnection.getLogger())
//        val signalRFuture: SignalRFuture<Void> = mHubConnection.start(clientTransport)
//        try {
//            signalRFuture.get()
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//            return
//        } catch (e: ExecutionException) {
//            e.printStackTrace()
//            return
//        }
//        val HELLO_MSG = "Hello from Android!"
//        sendMessage(HELLO_MSG)
//        val CLIENT_METHOD_BROADAST_MESSAGE = "broadcastMessage"
//        mHubProxy.on(CLIENT_METHOD_BROADAST_MESSAGE,
//            object : SubscriptionHandler1<CustomMessage?>() {
//                fun run(msg: CustomMessage) {
//                    val finalMsg: String = msg.UserName.toString() + " says " + msg.Message
//                    // display Toast message
//                    mHandler.post(Runnable {
//                        Toast.makeText(
//                            ApplicationProvider.getApplicationContext<Context>(),
//                            finalMsg,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    })
//                }
//            }, CustomMessage::class.java)
//    }
//}