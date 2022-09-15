package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting.EditEmail
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting.EditPassword
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting.EditPhone
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting.EditUserName

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
        val user = SessionManager(context).user

        username.text = user?.userFullname
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