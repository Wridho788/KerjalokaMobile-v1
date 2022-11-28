package com.ciptakerjaarunika.kerjaloka.ui.Global

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.google.android.material.button.MaterialButton

class GlobalDeleteModal : SuperBottomSheetFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_konfirmasi = view.findViewById<MaterialButton>(R.id.btnKonfirmasi)
        val edit_konfirmasi = view.findViewById<EditText>(R.id.confirm_terminate)
        btn_konfirmasi.setOnClickListener {
            var konfimasi_text = edit_konfirmasi.text.toString()
            if (konfimasi_text.length == 0) {
                Toast.makeText(context, "Kolom konfirmasi tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            } else if (konfimasi_text.toLowerCase() === "hapus") {
                Toast.makeText(
                    context,
                    "Mohon perhatikan penggunakan kapitalisasi",
                    Toast.LENGTH_SHORT
                )
                    .show()

            } else if (konfimasi_text == "Hapus") {
//                Toast.makeText(context, "Hapus Akun ", Toast.LENGTH_SHORT).show()
                ProfileAPI().GetDeactivatedAccount(context) {
                    SessionManager(context).access_token = null
                    SessionManager(context).user = null
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(context, "Kata yang kamu masukkan tidak valid", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_global_delete_account, container, false)
    }
}