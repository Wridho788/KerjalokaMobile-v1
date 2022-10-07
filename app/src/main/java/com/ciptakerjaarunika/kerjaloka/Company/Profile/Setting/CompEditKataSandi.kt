package com.ciptakerjaarunika.kerjaloka.Company.Profile.Setting

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.google.android.material.button.MaterialButton

class CompEditKataSandi(val iRefreshData: iRefreshData) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_comp_edit_kata_sandi, container, false)

        val oldPass = view.findViewById<EditText>(R.id.oldPass)
        val newPass = view.findViewById<EditText>(R.id.newPass)
        val confPass = view.findViewById<EditText>(R.id.confPass)
        val btnSimpan = view.findViewById<MaterialButton>(R.id.btn_simpan_kata_sandi)

        btnSimpan.setOnClickListener{
            val password = oldPass.text.toString()
            val newpassword = newPass.text.toString()
            company_profile_api().ChangePassword(password, newpassword, context){
                if (it != null) {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        view.findViewById<MaterialButton>(R.id.back_btn).setOnClickListener {
            fragmentManager?.popBackStack()
            iRefreshData.refresh()
        }


        return view
    }
}