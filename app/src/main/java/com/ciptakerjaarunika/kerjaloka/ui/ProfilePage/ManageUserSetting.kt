package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.HomePage
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting.EditEmail
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting.EditPassword
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting.EditPhone
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting.EditUserName
import com.google.android.material.button.MaterialButton

class ManageUserSetting : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manage_profile_setting_layout, container, false)

        val username = view.findViewById<TextView>(R.id.profile_username)
        val email = view.findViewById<TextView>(R.id.profile_email)
        val phone = view.findViewById<TextView>(R.id.profile_nomor_telepon)
        val password = view.findViewById<TextView>(R.id.profile_kata_sandi)
        val editUserName = view.findViewById<TextView>(R.id.edit_username_setting)
        val editEmail = view.findViewById<TextView>(R.id.edit_email_profile_setting)
        val editPhone = view.findViewById<TextView>(R.id.edit_nomor_telepon_setting)
        val editPassword = view.findViewById<TextView>(R.id.edit_kata_sandi)

        editEmail.setOnClickListener{
            replaceFragment(EditEmail())
        }
        editUserName.setOnClickListener{
            replaceFragment(EditUserName())
        }
        editPhone.setOnClickListener{
            replaceFragment(EditPhone())
        }
        editPassword.setOnClickListener{
            replaceFragment(EditPassword())
        }

        view.findViewById<MaterialButton>(R.id.btn_logout).setOnClickListener{
            ProfileAPI().Logout(context){
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            }
        }
        val user = SessionManager(context).user

        username.text = user?.username
        email.text = user?.email
        phone.text = user?.phone


        return view
    }

    companion object {
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }
}