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
class CompEditUsername(val iRefreshData: iRefreshData) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view =inflater.inflate(R.layout.fragment_comp_edit_username, container, false)

        val btnSave = view.findViewById<MaterialButton>(R.id.btnSaveUsername)

        btnSave.setOnClickListener{
            val username = view.findViewById<EditText>(R.id.js_EditusrName)?.text.toString()
            if (!username.isNullOrEmpty()){
                company_profile_api().ChangeUsername(username, context){
                    if (it != null){
                        fragmentManager?.popBackStack()
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(activity, "Username boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
        view.findViewById<MaterialButton>(R.id.back_btn).setOnClickListener {
            fragmentManager?.popBackStack()
            iRefreshData.refresh()
        }

        return view
    }
}