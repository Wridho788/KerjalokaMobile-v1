package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.IncomingCallFragmentBinding
import com.ciptakerjaarunika.kerjaloka.model.Interview.incoming_call_model
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.giphy.sdk.analytics.GiphyPingbacks.context
import org.jitsi.meet.sdk.BroadcastIntentHelper
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetUserInfo


class IncomingCallActivity() : AppCompatActivity(){
    private lateinit var binding : IncomingCallFragmentBinding
    private var vib: Vibrator? = null
    private var mp: MediaPlayer? = null
    private var playRingtone : MediaPlayer? = null;

    // Example for sending actions to JitsiMeetSDK
    private fun hangUp() {
        val hangupBroadcastIntent: Intent = BroadcastIntentHelper.buildHangUpIntent()
        baseContext?.applicationContext?.let { LocalBroadcastManager.getInstance(it).sendBroadcast(hangupBroadcastIntent) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mp = MediaPlayer.create(baseContext, Settings.System.DEFAULT_RINGTONE_URI);
        vib = baseContext?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator;
        if (Build.VERSION.SDK_INT >= 26) {
            vib!!.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vib!!.vibrate(500)
        }
        playRingtone = mp
        playRingtone?.start()

//        binding.name.text = data.name

//        context?.let {
//                Glide.with(it)
//                    .load(config().portAddress + "/photo/Profile/" + data.photo).fitCenter()
//                    .into(binding.photo)
//        }
        binding.rejectBtn?.setOnClickListener{
            playRingtone?.stop()
            fragmentManager?.popBackStack()
        }
        binding.approveBtn?.setOnClickListener{
            fragmentManager?.popBackStack()
            playRingtone?.stop()
            val userInfo = JitsiMeetUserInfo();
            userInfo.email = SessionManager(context).user?.email
            userInfo.displayName = SessionManager(context).user?.userFullname

            if(SessionManager(context).user?.company != null){
                userInfo.displayName = SessionManager(context).user?.company?.companyName
            }

//            val options = JitsiMeetConferenceOptions.Builder()
//                .setRoom(data.roomId)
//                .setUserInfo(userInfo)
//                .build()
//            context?.let { it1 -> JitsiMeetActivity.launch(it1, options) }
        }

    }
}