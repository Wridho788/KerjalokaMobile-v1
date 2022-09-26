package com.ciptakerjaarunika.kerjaloka.Company.Profile.Setting

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.ui.Global.otpVerification
import com.google.android.material.button.MaterialButton

class CompEditEmail : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_comp_edit_email, container, false)

        val newEmail = view.findViewById<EditText>(R.id.comp_EditusrEmail)
        val btnSave = view.findViewById<MaterialButton>(R.id.btnSaveEmail)

        btnSave.setOnClickListener {
            val email = newEmail?.text.toString()
            company_profile_api().checkNewEmail(email, context) { checkResponse ->
                    company_profile_api().ChangeEmail(email, context) { changeEmail ->
                        replaceFragment(otpVerification(), changeEmail?.token)
                }
            }
        }

        return view
    }

    companion object {
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