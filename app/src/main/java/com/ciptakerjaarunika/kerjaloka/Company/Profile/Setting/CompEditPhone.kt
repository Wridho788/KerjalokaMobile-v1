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

class CompEditPhone : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_comp_edit_phone, container, false)

        val btnSave = view.findViewById<MaterialButton>(R.id.btnSaveNewPhone)

        btnSave.setOnClickListener{
            val phone = view.findViewById<EditText>(R.id.comp_EditusrPhone)?.text.toString()
            company_profile_api().checkPhone(phone, context){
                company_profile_api().ChangeNumber(phone, context){
                    replaceFragment(otpVerification(), it?.Token)
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
        mBundle.putString(otpVerification.EXTRA_DESCRIPTION, "Lifestyle")

        val description = "Kategori ini akan berisi produk-produk lifestyle"
        otpVerificationFragment.arguments = mBundle
        otpVerificationFragment.description = token
        val mFragmentManager = parentFragmentManager
        mFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_container, otpVerificationFragment, otpVerification::class.java.simpleName)
            addToBackStack(null)
            commit()

        }
//        val fragmentManager = activity?.supportFragmentManager
//        val fragmentTransaction = fragmentManager?.beginTransaction()
//        fragmentTransaction?.replace(R.id.fragment_container, fragment)
//        fragmentTransaction?.commit()
    }

}