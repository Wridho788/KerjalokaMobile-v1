package com.ciptakerjaarunika.kerjaloka.ui.Global

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.ui.AkunPage.AkunPage
import com.google.android.material.button.MaterialButton


class otpVerification : Fragment() {

    private lateinit var timer: CountDownTimer
    var description: String? = null

    private var countdown_timer: CountDownTimer? = null
    private var time_in_milliseconds = 60000L
    private var pauseOffSet = 0L

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSend = view.findViewById<MaterialButton>(R.id.sendOTP)
        val btnResend = view.findViewById<TextView>(R.id.btn_resend)
//        timer = object : CountDownTimer(60000, 1000) {
//            override fun onTick(p0: Long) {
//                ticker.text = (p0 / 1000).toString()
//            }
//
//            override fun onFinish() {
//                Log.d("onFinish", "finish")
////                replaceFragment(AkunPage())
//            }
//        }
        starTimer(pauseOffSet)


        if (arguments != null) {
            val descFromBundle = arguments?.getString(EXTRA_DESCRIPTION)
            val descPhone = arguments?.getString(EXTRA_DESCRIPTION_PHONE)
            val descNewPhone = arguments?.getString(EXTRA_DESCRIPTION_NEW_PHONE)
            val descEmail = arguments?.getString(EXTRA_DESCRIPTION_EMAIL)

            val phoneTextView = view.findViewById<TextView>(R.id.phone_verif)
            var token = descFromBundle
            Log.d("token first", token.toString())

            if (description == "phone") {
                phoneTextView.text = descPhone

                btnResend.setOnClickListener {
//                    timer.onFinish()
                    resetTimer()
                    starTimer(pauseOffSet)
                    Log.d("new phone", descNewPhone.toString())
                    company_profile_api().checkPhone(descNewPhone.toString(), context) {
                        company_profile_api().ChangeNumber(
                            descNewPhone.toString(),
                            context
                        ) { it1 ->
                            if (it1?.code == 210) {
                                Log.d("new token", it1.token.toString())
//                                token = it1.token
//                                Log.d("token new", token.toString())

                            }
                        }
                    }
                    Toast.makeText(context, "Resend phone", Toast.LENGTH_SHORT).show()
                }
            } else if (description == "email") {
                phoneTextView.text = descEmail
                btnSend.setOnClickListener {
                    resetTimer()
                    Toast.makeText(context, "ReSend email", Toast.LENGTH_SHORT).show()
                }
            }


//            description = descFromBundle
            btnSend.setOnClickListener {
                val otp1 = view.findViewById<EditText>(R.id.otp1)?.text.toString()
                val otp2 = view.findViewById<EditText>(R.id.otp2)?.text.toString()
                val otp3 = view.findViewById<EditText>(R.id.otp3)?.text.toString()
                val otp4 = view.findViewById<EditText>(R.id.otp4)?.text.toString()
                val otp5 = view.findViewById<EditText>(R.id.otp5)?.text.toString()
                val otp6 = view.findViewById<EditText>(R.id.otp6)?.text.toString()
                val code = "${otp1 + otp2 + otp3 + otp4 + otp5 + otp6}"
//                Log.d("token new", token.toString())

                if (description == "phone") {
//                    Log.d("token new desc", description.toString())
//                    Log.d("token new phone", token.toString())

                    company_profile_api().PhoneChangeVerification(token.toString(), code, context) {
                        if (it != null) {
                            if (it.code == "210") {
                                resetTimer()
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                replaceFragment(AkunPage())
                            } else {
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, it?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else if (description == "email") {
                    Log.d("token email", description.toString())
                    Log.d("token email", token.toString())
                    company_profile_api().EmailChangeVerification(token.toString(), code, context) {
                        Log.d("token email", it.toString())
                        if (it != null) {
                            Log.d("token email not null", it.toString())
                            if (it.code == "210") {
                                resetTimer()
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                replaceFragment(AkunPage())
                            } else {
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        if (savedInstanceState != null) {
        }

    }

    private fun starTimer(pauseOffSetL: Long) {
        val ticker = view?.findViewById<TextView>(R.id.time)

        countdown_timer = object : CountDownTimer(time_in_milliseconds - pauseOffSetL, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                pauseOffSet = time_in_milliseconds - millisUntilFinished
                ticker?.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                Toast.makeText(context, "Timer finished", Toast.LENGTH_LONG).show()
            }
        }.start()
    }

//    private fun pauseTimer(){
//        if (countdown_timer!= null){
//            countdown_timer!!.cancel()
//        }
//    }

    private fun resetTimer() {
        val ticker = view?.findViewById<TextView>(R.id.time)
        if (countdown_timer != null) {
            countdown_timer!!.cancel()
            ticker?.text = " ${(time_in_milliseconds / 1000)}"
            countdown_timer = null
            pauseOffSet = 0
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_otp_verification, container, false)

        return view
    }

//    override fun onStart() {
//        super.onStart()
//        timer.start()
//    }

    companion object {
        var EXTRA_DESCRIPTION = "extra_description"
        var EXTRA_DESCRIPTION_PHONE = "phone"
        var EXTRA_DESCRIPTION_EMAIL = "email"
        var EXTRA_DESCRIPTION_NEW_PHONE = "newPhone"
        var EXTRA_DESCRIPTION_NEW_EMAIL = "newEmail"
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(id, fragment)
        fragmentTransaction?.commit()
    }
}