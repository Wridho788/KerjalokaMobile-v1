package com.ciptakerjaarunika.kerjaloka.ui.Global

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.google.android.material.button.MaterialButton


class otpVerification() : Fragment() {

    private lateinit var timer: CountDownTimer
    var description: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSend = view.findViewById<MaterialButton>(R.id.sendOTP)
        val ticker = view.findViewById<TextView>(R.id.time)

        timer = object : CountDownTimer(60000, 1000){
            override fun onTick(p0: Long) {
                ticker.text = (p0 / 1000).toString()
            }

            override fun onFinish() {
                TODO("Not yet implemented")
            }

        }

        if (arguments != null){
            val descFromBundle = arguments?.getString(EXTRA_DESCRIPTION)
            description = descFromBundle

        }

        if (savedInstanceState != null) {
        }

        btnSend.setOnClickListener{
            val otp1 = view.findViewById<EditText>(R.id.otp1)?.text.toString()
            val otp2 = view.findViewById<EditText>(R.id.otp2)?.text.toString()
            val otp3 = view.findViewById<EditText>(R.id.otp3)?.text.toString()
            val otp4 = view.findViewById<EditText>(R.id.otp4)?.text.toString()
            val otp5 = view.findViewById<EditText>(R.id.otp5)?.text.toString()
            val otp6 = view.findViewById<EditText>(R.id.otp6)?.text.toString()
            val code = "${otp1+otp2+otp3+otp4+otp5+otp6}"
            company_profile_api().ChangeVerification(description, code, context){}
        }

//        return view
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_otp_verification, container, false)
//        super.onViewCreated(view,savedInstanceState)
//
//        val btnSend = view.findViewById<MaterialButton>(R.id.sendOTP)
//        val ticker = view.findViewById<TextView>(R.id.time)
//
//        timer = object : CountDownTimer(60000, 1000){
//            override fun onTick(p0: Long) {
//                ticker.text = (p0 / 1000).toString()
//            }
//
//            override fun onFinish() {
//                TODO("Not yet implemented")
//            }
//
//        }
//
//        if (savedInstanceState != null) {
//            val descFromBundle = savedInstanceState.getString(EXTRA_DESCRIPTION)
//            description = descFromBundle
//        }
//
//        btnSend.setOnClickListener{
//            val otp1 = view.findViewById<EditText>(R.id.otp1)?.text.toString()
//            val otp2 = view.findViewById<EditText>(R.id.otp2)?.text.toString()
//            val otp3 = view.findViewById<EditText>(R.id.otp3)?.text.toString()
//            val otp4 = view.findViewById<EditText>(R.id.otp4)?.text.toString()
//            val otp5 = view.findViewById<EditText>(R.id.otp5)?.text.toString()
//            val otp6 = view.findViewById<EditText>(R.id.otp6)?.text.toString()
//            val code = "${otp1+otp2+otp3+otp4+otp5+otp6}"
//            company_profile_api().ChangeVerification(description, code, context){}
//        }

        return view
    }

    override fun onStart() {
        super.onStart()
//        timer.start()
    }

    companion object {
        var EXTRA_DESCRIPTION = "extra_description"

    }
}