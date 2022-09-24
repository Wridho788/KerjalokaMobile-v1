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

                }
            }
        }

        return view
    }

    companion object {
    }
}