package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.UserSetting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.profilepage
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
            var password = oldPass.text.toString()
            var newpassword = newPass.text.toString()
            if (password.length == 0 && newpassword.length == 0) {
                Toast.makeText(context, "Mohon masukkan kata sandi", Toast.LENGTH_SHORT).show()
            } else if (password.length == 0) {
                Toast.makeText(context, "Mohon masukkan kata sandi", Toast.LENGTH_SHORT).show()
            } else if (newpassword.length == 0) {
                Toast.makeText(context, "Masukkan kata sandi baru", Toast.LENGTH_SHORT).show()
            } else if (confPass.text.toString().length == 0) {
                Toast.makeText(context, "Masukkan konfirmasi kata sandi", Toast.LENGTH_SHORT).show()
            } else if (!newpassword.matches(".*[0-9].*".toRegex())) {
                view?.findViewById<TextView>(R.id.password_rules_1)?.visibility = View.VISIBLE
                view?.findViewById<TextView>(R.id.password_rules_1)?.text =
                    "Kata Sandi Harus Berisi Angka"
            } else if (!newpassword.matches(".*[A-Z].*".toRegex())) {
                view?.findViewById<TextView>(R.id.password_rules_1)?.visibility = View.VISIBLE
                view?.findViewById<TextView>(R.id.password_rules_1)?.text =
                    "Kata Sandi Harus Berisi Huruf Kapital"
            } else if (newpassword != confPass.text.toString()) {
                Toast.makeText(
                    context,
                    "Konfirmasi sandi tidak sama dengan password baru",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (newpassword == password) {
                Toast.makeText(
                    context,
                    "Kata sandi tidak boleh sama dengan sebelumnya",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                company_profile_api().ChangePassword(
                    password,
                    newpassword,
                    context
                ) {
                    if (it != null) {
                        if (it.code == "210") {
                            Toast.makeText(
                                activity,
                                "Berhasil Mengubah Password",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            fragmentManager?.popBackStack()
                            view?.findViewById<TextView>(R.id.password_rules_1)?.visibility =
                                View.GONE
                        }
                    } else {
                        Toast.makeText(activity, it?.message, Toast.LENGTH_SHORT).show()
                    }
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