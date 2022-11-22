package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Global.ModalDeactivateAccount
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting.EditEmail
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting.EditPassword
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting.EditPhone
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting.EditUserName
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson

class ManageUserSetting : Fragment() {

    var setNewsletter: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.fragment_manage_profile_setting_layout, container, false)
        val username = view.findViewById<TextView>(R.id.profile_username)
        val email = view.findViewById<TextView>(R.id.profile_email)
        val phone = view.findViewById<TextView>(R.id.profile_nomor_telepon)
        val password = view.findViewById<TextView>(R.id.profile_kata_sandi)
        val editUserName = view.findViewById<TextView>(R.id.edit_username_setting)
        val editEmail = view.findViewById<TextView>(R.id.edit_email_profile_setting)
        val editPhone = view.findViewById<TextView>(R.id.edit_nomor_telepon_setting)
        val editPassword = view.findViewById<TextView>(R.id.edit_kata_sandi)
        val discover = view.findViewById<Switch>(R.id.switchDiscoverable)
        val newsletter = view.findViewById<Switch>(R.id.switchNewsLetter)
        val btnDeactive = view.findViewById<MaterialButton>(R.id.btn_nonaktifkan_akun)
        val user = SessionManager(context).user

        editEmail.setOnClickListener {
            editEmailFragment(user?.email)
        }
        editUserName.setOnClickListener {
            replaceFragment(EditUserName())
        }
        editPhone.setOnClickListener {
            editPhoneFragment(user?.phone)
        }
        editPassword.setOnClickListener {
            replaceFragment(EditPassword())
        }

        view.findViewById<MaterialButton>(R.id.btn_logout).setOnClickListener {
            ProfileAPI().Logout(SessionManager(context).device_token, context) {
                val intent = Intent(context, MainActivity()::class.java)
                startActivity(intent)
            }
        }

        ProfileAPI().JobseekerGetProfileData(context) {
            discover.isChecked = it?.data?.users?.isDiscoverable ?: false
            newsletter.isChecked = it?.data?.users?.isNewsletter ?: false

            newsletter.setOnClickListener { it1 ->
                if (newsletter.isChecked == true) {
                    setNewsletter = true
                    company_profile_api().newsletter(setNewsletter, context) {}
                } else {
                    setNewsletter = false
                    company_profile_api().newsletter(setNewsletter, context) {}
                }
            }
            discover.setOnClickListener {
                if (discover.isChecked == true) {
                    company_profile_api().discoverable(context) {}
                } else {
                    company_profile_api().undiscoverable(context) {}
                }
            }
        }

        btnDeactive.setOnClickListener {
            val sheet = ModalDeactivateAccount()
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager,
                    "DemoBottomSheetFragment"
                )
            }
        }

        username.text = user?.username
        email.text = user?.email
        phone.text = user?.phone
        return view
    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }


    private fun editEmailFragment(data: String?) {
        val editEmailFragment = EditEmail()
        val mBundle = Bundle()
        val data = Gson().toJson(data)
        mBundle.putString(EditEmail.EXTRA_USER_DATA, data)
        editEmailFragment.arguments = mBundle
        val mFragmentManager = parentFragmentManager
        mFragmentManager.beginTransaction().apply {
            replace(
                R.id.fragment_container,
                editEmailFragment,
                EditEmail::class.java.simpleName
            )
            addToBackStack(null)
            commit()

        }
    }

    private fun editPhoneFragment(data: String?) {
        val user = SessionManager(context).user
        val editPhoneFragment = EditPhone(user?.phone!!)
        val mBundle = Bundle()
        val data = Gson().toJson(data)
        mBundle.putString(EditPhone.EXTRA_USER_DATA, data)
        editPhoneFragment.arguments = mBundle
        val mFragmentManager = parentFragmentManager
        mFragmentManager.beginTransaction().apply {
            replace(
                R.id.fragment_container,
                editPhoneFragment,
                EditPhone::class.java.simpleName
            )
            addToBackStack(null)
            commit()
        }
    }
}