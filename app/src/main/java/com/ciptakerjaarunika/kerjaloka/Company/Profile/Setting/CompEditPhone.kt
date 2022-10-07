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
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.ui.Global.otpVerification
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.delay

class CompEditPhone(val iRefreshData: iRefreshData) : Fragment() {
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
            val keyword = view.findViewById<EditText>(R.id.comp_EditusrPhone)?.text.toString()
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

        view.findViewById<MaterialButton>(R.id.back_btn).setOnClickListener {
            fragmentManager?.popBackStack()
            iRefreshData.refresh()
        }

        return view
    }

    companion object {
    }

    @SuppressLint("RestrictedApi")
    private fun replaceFragment(fragment: Fragment, token: String?) {
        val otpVerificationFragment = otpVerification()
        val mBundle = Bundle()
        mBundle.putString(otpVerification.EXTRA_DESCRIPTION, token)

        otpVerificationFragment.arguments = mBundle
        otpVerificationFragment.description = "phone"
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