package com.ciptakerjaarunika.kerjaloka.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.InterviewPage.IncomingCallActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class KerjalokaMessagingService() :FirebaseMessagingService() {
    private var playRingtone : MediaPlayer? = null;
    override fun onMessageReceived(message: RemoteMessage) {
        if(message?.notification != null) {
            if (message.notification!!.title != "IncomingCall") {
//                if(ActivityManager.RunningAppProcessInfo().importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                    generateNotification(message.notification!!.title!!, message.notification!!.body!!, false)
//                }
            }
//            else{
//                if(ActivityManager.RunningAppProcessInfo().importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
//                    generateNotification(message.notification!!.title!!, message.notification!!.body!!, true)
//
////
////                    val intentToMain = Intent(baseContext, MainActivity::class.java)
////                    intentToMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////                    startActivity(intentToMain)
//                }
//            }
        }
    }
    // Generate the notification
    fun getRemoteView(title: String, message: String):RemoteViews{
        val remoteViews = RemoteViews(config().channelName, R.layout.notification)
        remoteViews.setTextViewText(R.id.title_notification, title)
        remoteViews.setTextViewText(R.id.message_notification, message)
        return remoteViews
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateNotification(title : String, message :String, isCall : Boolean){
            var intent : Intent?  = null
        if(!isCall) {
            intent = Intent(this, MainActivity::class.java)
        }else{
            intent = Intent(this, IncomingCallActivity::class.java)
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_IMMUTABLE)
        var builder : NotificationCompat.Builder = NotificationCompat.Builder(this, config().channelId)
            .setSmallIcon(R.drawable.kerjaloka_logo_small)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setContent(getRemoteView(title, message))


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(config().channelId, config().channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, builder.build())
    }

    // Attach the notification created with the custom layout
    // Show the notification
}