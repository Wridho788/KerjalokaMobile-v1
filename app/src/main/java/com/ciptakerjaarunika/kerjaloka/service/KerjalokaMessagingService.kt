package com.ciptakerjaarunika.kerjaloka.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.dropbox.core.DbxSdkVersion.Version
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import okhttp3.internal.notify

class KerjalokaMessagingService() :FirebaseMessagingService() {


    override fun onMessageReceived(message: RemoteMessage) {
        if(message?.notification != null){
            generateNotification(message.notification!!.title!!, message.notification!!.body!!)
        }
    }
    // Generate the notification
    fun getRemoteView(title: String, message: String):RemoteViews{
        val remoteViews = RemoteViews(config().channelName, R.layout.notification)
        remoteViews.setTextViewText(R.id.title_notification, title)
        remoteViews.setTextViewText(R.id.message_notification, message)
        return remoteViews
    }

    fun generateNotification(title : String, message :String){
            val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
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