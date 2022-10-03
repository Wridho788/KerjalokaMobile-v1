package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.ciptakerjaarunika.kerjaloka.Company.Profile.user
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.ui.Global.otpVerification
import com.google.android.material.button.MaterialButton

class EditPhone : Fragment() {

    var userData: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val txtPhone = view.findViewById<TextView>(R.id.current_user_nomor_telepon_2)
        val newPhone = view.findViewById<EditText>(R.id.masukkan_nomor_telepon_baru)
        val btnSave = view.findViewById<MaterialButton>(R.id.btn_simpan_nomor_telepon)
        if (arguments != null) {
            val descFromBundle = arguments?.getString(EditEmail.EXTRA_USER_DATA)
            userData = descFromBundle
            txtPhone.text = userData
        }
        btnSave.setOnClickListener{
            val keyword = newPhone.text.toString()
            company_profile_api().checkPhone(keyword, context){
                company_profile_api().ChangeNumber(keyword, context){ it1 ->
                    if (it1?.code == 210){
                        replaceFragment(otpVerification(), it1?.token)
                    }
                }
                if (it?.exists == false){

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