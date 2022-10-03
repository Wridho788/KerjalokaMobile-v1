package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.google.android.material.button.MaterialButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditPassword.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditPassword : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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

        btnSimpan.setOnClickListener{
            val password = oldPass.text.toString()
            val newpassword = newPass.text.toString()
            company_profile_api().ChangePassword(password, newpassword, context){}
        }



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditPassword.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditPassword().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}