package com.ciptakerjaarunika.kerjaloka.Company.Profile.Setting

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.ui.Global.otpVerification
import com.google.android.material.button.MaterialButton

class CompEditEmail(val iRefreshData: iRefreshData, var email: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_comp_edit_email, container, false)

        val newEmail = view.findViewById<EditText>(R.id.comp_EditusrEmail)
        val btnSave = view.findViewById<MaterialButton>(R.id.btnSaveEmail)
        val txtEmail = view.findViewById<TextView>(R.id.comp_email)
        txtEmail.text = email

        view.findViewById<MaterialButton>(R.id.back_btn).setOnClickListener {
            fragmentManager?.popBackStack()
            iRefreshData.refresh()
        }

        btnSave.setOnClickListener {
            val email = newEmail?.text.toString()
            if (email.length == 0) {
                Toast.makeText(context, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (email.isEmailValid()) {
                company_profile_api().checkNewEmail(email, context) { checkResponse ->
                    company_profile_api().ChangeEmail(email, context) { changeEmail ->
                        if (changeEmail?.code == 210) {
                            replaceFragment(
                                otpVerification(),
                                changeEmail.token,
                                email,
                                newEmail?.text.toString()
                            )
                        }
                    }
                }
            } else {
                Toast.makeText(context, "Email Tidak Valid", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    companion object;
    private fun String.isEmailValid(): Boolean {
        return this.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    @SuppressLint("RestrictedApi")
    private fun replaceFragment(
        fragment: Fragment,
        token: String?,
        email: String?,
        newEmail: String?
    ) {
        val otpVerificationFragment = otpVerification()
        val mBundle = Bundle()
        mBundle.putString(otpVerification.EXTRA_DESCRIPTION, token)
        mBundle.putString(otpVerification.EXTRA_DESCRIPTION_EMAIL, email)
        mBundle.putString(otpVerification.EXTRA_DESCRIPTION_NEW_EMAIL, newEmail)
        otpVerificationFragment.arguments = mBundle
        otpVerificationFragment.description = "email"
        val mFragmentManager = parentFragmentManager
        mFragmentManager.beginTransaction().apply {
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