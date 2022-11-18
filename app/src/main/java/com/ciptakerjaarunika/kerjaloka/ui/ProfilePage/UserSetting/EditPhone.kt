package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.ciptakerjaarunika.kerjaloka.ui.Global.otpVerification
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.profilepage
import com.google.android.material.button.MaterialButton

class EditPhone(var phone: String) : Fragment() {

    var userData: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val txtPhone = view.findViewById<TextView>(R.id.current_user_nomor_telepon_2)
        val newPhone = view.findViewById<EditText>(R.id.masukkan_nomor_telepon_baru)
        val btnSave = view.findViewById<MaterialButton>(R.id.btn_simpan_nomor_telepon)
        txtPhone.text = phone

        if (arguments != null) {
            val descFromBundle = arguments?.getString(EditEmail.EXTRA_USER_DATA)
            userData = descFromBundle
            Log.d("editPhone", userData.toString())
        }
        view.findViewById<MaterialButton>(R.id.back_btn).setOnClickListener {
            back()
        }

        btnSave.setOnClickListener {
            var keyword = newPhone.text.toString()
            if (keyword.length == 0) {
                Toast.makeText(context, "Phone Number Is Not Valid", Toast.LENGTH_SHORT).show()
            } else if (keyword.length < 8) {
                Toast.makeText(context, "Phone Number Is Not Valid", Toast.LENGTH_SHORT).show()
            } else {
                company_profile_api().checkPhone(keyword, context) {
                    if (it != null) {
                        if (it == false) {
                            company_profile_api().ChangeNumber(keyword, context) { it1 ->
                                if (it1?.code == 210) {
                                    replaceFragment(otpVerification(), it1.token, userData, keyword)
                                }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Phone number already exists",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_nomor_telepon_profile, container, false)
        return view
    }

    companion object {
        var EXTRA_USER_DATA = "extra_userData"
    }

    @SuppressLint("RestrictedApi")
    private fun replaceFragment(
        fragment: Fragment,
        token: String?,
        phone: String?,
        newPhone: String?
    ) {
        val otpVerificationFragment = otpVerification()
        val mBundle = Bundle()
        mBundle.putString(otpVerification.EXTRA_DESCRIPTION, token)
        mBundle.putString(otpVerification.EXTRA_DESCRIPTION_PHONE, phone)
        mBundle.putString(otpVerification.EXTRA_DESCRIPTION_NEW_PHONE, newPhone)

        otpVerificationFragment.arguments = mBundle
        otpVerificationFragment.description = "phone"
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

    private fun back() {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(id, profilepage(6), "Profile Page")
        fragmentTransaction.commit()
    }
}