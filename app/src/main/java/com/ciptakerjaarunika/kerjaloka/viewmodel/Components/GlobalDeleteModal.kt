package com.ciptakerjaarunika.kerjaloka.viewmodel.Components

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.google.android.material.button.MaterialButton
import java.util.*

class GlobalDeleteModal : SuperBottomSheetFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_konfirmasi = view.findViewById<MaterialButton>(R.id.btnKonfirmasi)
        val edit_konfirmasi = view.findViewById<EditText>(R.id.confirm_terminate)
        var konfimasi_text: String? = ""

        edit_konfirmasi.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (edit_konfirmasi.text.toString().isNullOrEmpty()) {
                    Toast.makeText(
                        context,
                        "Kolom Konfirmasi Tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(
                        "konfirmasi text, ${edit_konfirmasi.text}, Kolom Konfirmasi Tidak boleh kosong",
                        edit_konfirmasi.text.toString()
                    )
                } else if (edit_konfirmasi.text.toString()
                        .lowercase(Locale.getDefault()) == "hapus" && edit_konfirmasi.text.toString() != "Hapus"
                ) {
                    Toast.makeText(
                        context,
                        "Mohon Perhatikan Penggunaan Kapitalisasi",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(
                        "konfirmasi text, ${edit_konfirmasi.text}, Mohon Perhatikan Penggunaan Kapitalisasi",
                        edit_konfirmasi.text.toString()
                    )

                } else if (edit_konfirmasi.text.toString() != "Hapus") {
                    Toast.makeText(
                        context,
                        "Kata yang kamu masukkan tidak valid",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(
                        "konfirmasi text, ${edit_konfirmasi.text}, Kata yang kamu masukkan tidak valid",
                        edit_konfirmasi.text.toString()
                    )

                } else {
                    konfimasi_text = edit_konfirmasi.text.toString()
                }
            }
        })


        btn_konfirmasi.setOnClickListener {
            ProfileAPI().GetDeactivatedAccount(context) {
                if (it != null) {
                    Log.d("terminate", it.toString())
                }
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