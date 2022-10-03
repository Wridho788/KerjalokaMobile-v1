package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.ui.Global.otpVerification
import com.google.android.material.button.MaterialButton


class EditEmail : Fragment() {

    var userData: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null){
            val descFromBundle = arguments?.getString(EXTRA_USER_DATA)
            userData = descFromBundle
            val newEmail = view.findViewById<EditText>(R.id.masukkan_email_baru_edit_email)
            val btnSave = view.findViewById<MaterialButton>(R.id.btn_simpan_username)
            val email = view.findViewById<TextView>(R.id.current_user_email_2)
            email.text = userData
            btnSave.setOnClickListener {
                val email = newEmail?.text.toString()
                company_profile_api().checkNewEmail(email, context) { checkResponse ->
                    company_profile_api().ChangeEmail(email, context) { changeEmail ->
                        replaceFragment(otpVerification(), changeEmail?.token)
                    }
                }
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_email_profile, container, false)

        return view
    }

    companion object {
        var EXTRA_USER_DATA = "extra_userData"
    }


    private fun replaceFragment(fragment: Fragment, token: String?) {
        val otpVerificationFragment = otpVerification()
        val mBundle = Bundle()
        mBundle.putString(otpVerification.EXTRA_DESCRIPTION, token)

        otpVerificationFragment.arguments = mBundle
        otpVerificationFragment.description = token
        val mFragmentManager = parentFragmentManager
        mFragmentManager?.beginTransaction()?.apply {
            replace(
                R.id.fragment_container,
                otpVerificationFragment,
                otpVerification::class.java.simpleName
            )
            addToBackStack(null)
            commit()

        }
    }
}