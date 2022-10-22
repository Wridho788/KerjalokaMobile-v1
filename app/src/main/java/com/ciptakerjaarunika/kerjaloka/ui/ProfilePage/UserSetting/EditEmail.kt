package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.ui.Global.otpVerification
import com.google.android.material.button.MaterialButton


class EditEmail : Fragment() {

    var userData: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val descFromBundle = arguments?.getString(EXTRA_USER_DATA)
            userData = descFromBundle
            val newEmail = view.findViewById<EditText>(R.id.masukkan_email_baru_edit_email)
            val btnSave = view.findViewById<MaterialButton>(R.id.btn_simpan_username)
            val email = view.findViewById<TextView>(R.id.current_user_email_2)
            email.text = userData
            btnSave.setOnClickListener {
                company_profile_api().checkNewEmail(
                    newEmail?.text.toString(),
                    context
                ) {
                    company_profile_api().ChangeEmail(newEmail?.text.toString(), context) { it1 ->
                        if (it1?.code == 210) {
                            replaceFragment(otpVerification(), it1.token, userData)
                        }
                    }
                }
            }
            view.findViewById<MaterialButton>(R.id.back_btn).setOnClickListener {
                fragmentManager?.popBackStack()
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


    @SuppressLint("RestrictedApi")
    private fun replaceFragment(fragment: Fragment, token: String?, email: String?) {
        val otpVerificationFragment = otpVerification()
        val mBundle = Bundle()
        mBundle.putString(otpVerification.EXTRA_DESCRIPTION, token)
        mBundle.putString(otpVerification.EXTRA_DESCRIPTION_EMAIL, email)

        otpVerificationFragment.arguments = mBundle
        otpVerificationFragment.description = "email"
        val mFragmentManager = parentFragmentManager
        mFragmentManager.beginTransaction().apply {
            replace(
                id,
                otpVerificationFragment,
                otpVerification::class.java.simpleName
            )
            addToBackStack(null)
            commit()

        }
    }
}