package com.ciptakerjaarunika.kerjaloka.ui.Global

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.fragment_company_job_active_page
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.Data
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.Review
import com.ciptakerjaarunika.kerjaloka.Company.Profile.data
import com.ciptakerjaarunika.kerjaloka.Company.Profile.user
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.UsersAPI
import com.ciptakerjaarunika.kerjaloka.model.User.User
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson

class ModalDeactivateAccount : SuperBottomSheetFragment() {

    var password : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email = view.findViewById<EditText>(R.id.comp_email)
        val pswd = view.findViewById<EditText>(R.id.comp_pswd)
        val btnSave = view.findViewById<MaterialButton>(R.id.btnSave)
            email.setText(SessionManager(context).user?.email)
            btnSave.setOnClickListener{
                password = pswd.text.toString()
                UsersAPI().DeactiveAccount(password!!, context){}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modal_deactivate_account, container, false)
    }

    companion object {
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }
}