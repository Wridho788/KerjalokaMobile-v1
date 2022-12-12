package com.ciptakerjaarunika.kerjaloka.ui.Global

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        val layoutDeactivate = view.findViewById<LinearLayout>(R.id.layout_deactivate)

        email.setText(SessionManager(context).user?.email)
        btnSave.setOnClickListener {
            spinner.visibility = View.VISIBLE
            layoutDeactivate.visibility = View.GONE
            if (pswd.text.toString().length != 0) {
                UsersAPI().DeactiveAccount(pswd.text.toString(), context) { it ->
                    Log.d("deactivate", it.toString())
                    if (it != null) {
                        if(it.code == "210") {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            spinner.visibility = View.GONE
                            layoutDeactivate.visibility = View.VISIBLE
                            this.dismiss()
                            val intent = Intent(context, MainActivity()::class.java)
                            startActivity(intent)
                        } else {
                            spinner.visibility = View.GONE
                            layoutDeactivate.visibility = View.VISIBLE
                        }
                    } else {
                        Log.d("deactivate err", it.toString())
                        spinner.visibility = View.GONE
                        layoutDeactivate.visibility = View.VISIBLE
                    }
                    spinner.visibility = View.GONE
                    layoutDeactivate.visibility = View.VISIBLE
                }
            } else {
                spinner.visibility = View.GONE
                layoutDeactivate.visibility = View.VISIBLE
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