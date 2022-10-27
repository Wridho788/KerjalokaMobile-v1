package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.profilepage
import com.google.android.material.button.MaterialButton

class EditPassword : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_kata_sandi_profile, container, false)
        val oldPass = view.findViewById<EditText>(R.id.masukkan_kata_sandi_lama)
        val newPass = view.findViewById<EditText>(R.id.masukkan_kata_sandi_baru)
        val confPass = view.findViewById<EditText>(R.id.konfirmasi_kata_sandi_baru)
        val btnSimpan = view.findViewById<MaterialButton>(R.id.btn_simpan_kata_sandi)

        btnSimpan.setOnClickListener {
            company_profile_api().ChangePassword(
                oldPass.text.toString(),
                newPass.text.toString(),
                context
            ) {
                if (it != null) {
                    if (it.code == "210") {
                        Toast.makeText(activity, "Berhasil Mengubah Password", Toast.LENGTH_SHORT)
                            .show()
                        back()
                    }
                } else {
                    Toast.makeText(activity, it?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        view.findViewById<MaterialButton>(R.id.back_btn).setOnClickListener {
            back()
        }
        return view
    }

    private fun back() {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(id, profilepage(6), "Profile Page")
        fragmentTransaction.commit()
    }
}