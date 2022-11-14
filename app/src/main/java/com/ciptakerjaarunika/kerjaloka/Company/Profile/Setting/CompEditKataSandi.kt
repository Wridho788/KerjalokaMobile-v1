package com.ciptakerjaarunika.kerjaloka.Company.Profile.Setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
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

        btnSimpan.setOnClickListener {
            val password = oldPass.text.toString()
            val newpassword = newPass.text.toString()
            if (password.length == 0 && newpassword.length == 0) {
                Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show()
            } else if( password.length == 0) {
                Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show()
            } else if (newpassword.length == 0) {
                Toast.makeText(context, "Please enter your new password", Toast.LENGTH_SHORT).show()
            } else {
                company_profile_api().ChangePassword(password, newpassword, context) {
                    if (it != null) {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
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