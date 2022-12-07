package com.ciptakerjaarunika.kerjaloka.ui.Global

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.UsersAPI
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.google.android.material.button.MaterialButton

class ModalDeactivateAccount : SuperBottomSheetFragment() {

    var password: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email = view.findViewById<EditText>(R.id.comp_email)
        val pswd = view.findViewById<EditText>(R.id.comp_pswd)
        val btnSave = view.findViewById<MaterialButton>(R.id.btnSave)
        val spinner = view.findViewById<LinearLayout>(R.id.spinner)
        email.setText(SessionManager(context).user?.email)
        btnSave.setOnClickListener {
            spinner.visibility = View.VISIBLE
            if (pswd.text.toString().length != 0) {
                UsersAPI().DeactiveAccount(pswd.text.toString(), context) {
                    if (it != null && context != null) {
                        spinner.visibility = View.GONE
                        this.dismiss()
                        val intent = Intent(context, MainActivity()::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            context,
                            "Terjadi kesalahan yang tidak diketahui",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    context,
                    "Mohon masukkan kata sandi",
                    Toast.LENGTH_SHORT
                ).show()
            }
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

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }
}