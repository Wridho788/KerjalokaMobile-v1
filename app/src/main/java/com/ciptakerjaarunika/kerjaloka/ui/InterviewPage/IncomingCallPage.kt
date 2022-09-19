package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Vibrator
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import org.jitsi.meet.sdk.*
import timber.log.Timber


class IncomingCallPage(val roomId: String) : Fragment(){
    private var vib: Vibrator? = null
    private var mp: MediaPlayer? = null
    private var playRingtone : MediaPlayer? = null;

    private var broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            onBroadcastReceived(intent)
        }
    }
    private fun registerForBroadcastMessages() {
        val intentFilter = IntentFilter()

        for (type in BroadcastEvent.Type.values()) {
            intentFilter.addAction(type.action)
        }

        context?.let { LocalBroadcastManager.getInstance(it).registerReceiver(broadcastReceiver, intentFilter) }
    }

    // Example for handling different JitsiMeetSDK events
    private fun onBroadcastReceived(intent: Intent?) {
        if (intent != null) {
            val event = BroadcastEvent(intent)
            when (event.type) {
                BroadcastEvent.Type.CONFERENCE_JOINED -> Timber.i("Conference Joined with url%s", event.getData().get("url"))
                BroadcastEvent.Type.PARTICIPANT_JOINED -> Timber.i("Participant joined%s", event.getData().get("name"))
                else -> Timber.i("Received event: %s", event.type)
            }
        }
    }

    // Example for sending actions to JitsiMeetSDK
    private fun hangUp() {
        val hangupBroadcastIntent: Intent = BroadcastIntentHelper.buildHangUpIntent()
        context?.applicationContext?.let { LocalBroadcastManager.getInstance(it).sendBroadcast(hangupBroadcastIntent) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mp = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
//        vib = context?.let { getSystemService(it, VIBRATOR_SERVICE::class.java). };
//        vib.vibrate(500);
        playRingtone = mp
        playRingtone?.start()
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        view?.findViewById<ImageView>(R.id.reject_btn)?.setOnClickListener{
            playRingtone?.stop()
            fragmentManager?.popBackStack()
        }
        view?.findViewById<ImageView>(R.id.approve_btn)?.setOnClickListener{
            fragmentManager?.popBackStack()
            playRingtone?.stop()
            val userInfo = JitsiMeetUserInfo();
            userInfo.email = SessionManager(context).user?.email
            userInfo.displayName = SessionManager(context).user?.userFullname

            if(SessionManager(context).user?.company != null){
                userInfo.displayName = SessionManager(context).user?.company?.companyName
            }

            val options = JitsiMeetConferenceOptions.Builder()
                .setRoom(roomId)
                .setUserInfo(userInfo)
                .build()
            JitsiMeetActivity.launch(context, options)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.incoming_call_fragment, container, false)
    }
}