package com.ciptakerjaarunika.kerjaloka.Company.Profile.Setting

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.core.view.isVisible
import com.airbnb.lottie.parser.ColorParser
import com.ciptakerjaarunika.kerjaloka.Company.Profile.*
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.api.users
import com.ciptakerjaarunika.kerjaloka.ui.Global.ModalDeactivateAccount
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.ChooseScore
import com.google.android.material.button.MaterialButton


class AccountSetting(val data: data?) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account_setting, container, false)

        val btn_editUsername = view.findViewById<TextView>(R.id.edit_username_setting)
        val btn_editPhone = view.findViewById<TextView>(R.id.edit_nomor_telepon_setting)
        val btn_editEmail = view.findViewById<TextView>(R.id.edit_email_profile_setting)
        val btn_editPswd = view.findViewById<TextView>(R.id.edit_kata_sandi)
        val btn_deactived = view.findViewById<MaterialButton>(R.id.btn_nonaktifkan_akun)
        val txt_usrName = view.findViewById<TextView>(R.id.profile_username)
        val txt_phone = view.findViewById<TextView>(R.id.profile_nomor_telepon)
        val txt_email = view.findViewById<TextView>(R.id.profile_email)
        val txt_addrees = view.findViewById<TextView>(R.id.comp_profile_address)
        val discover = view.findViewById<Switch>(R.id.switchDiscoverable)
        val newsletter = view.findViewById<Switch>(R.id.switchNewsLetter)
        val btn_connect = view.findViewById<MaterialButton>(R.id.connect)


        txt_usrName.text = data?.username
        txt_phone.text = data?.phone
        txt_email.text = data?.email
        txt_addrees.text = data?.companyAddress
        users().CompanyGetUserData(context) {
            discover.isChecked = it?.data?.isDiscoverable!!
            Log.d("onCreateView: ", it?.data?.isDiscoverable.toString())
            newsletter.isChecked = it?.data?.isNewsletter!!
            if (it?.data?.userGoogleId.isNullOrEmpty()){
                btn_connect.strokeColor= ColorStateList.valueOf(Color.parseColor("#FF6666"))
                btn_connect.setTextColor(ColorStateList.valueOf(Color.parseColor("#FF6666")))
                btn_connect.text="Hubungkan"
            }
            else{
                btn_connect.strokeColor= ColorStateList.valueOf(Color.parseColor("#FFDEDE"))
                btn_connect.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFDEDE")))
                btn_connect.isClickable=false
                btn_connect.text="Terkoneksi"
            }
            discover.setOnClickListener{
                if (discover.isChecked==true){
                    company_profile_api().discoverable(context){}
                }
                else{
                    company_profile_api().undiscoverable(context){}
                }
            }


            newsletter.setOnClickListener { it1 ->
                val setNl = it?.data.isNewsletter
                Log.d("asd", setNl.toString())
                company_profile_api().newsletter(setNl, context){}
            }
        }


        btn_editUsername.setOnClickListener {
            replaceFragment(CompEditUsername())
        }
        btn_editEmail.setOnClickListener {
            replaceFragment(CompEditEmail())
        }
        btn_editPswd.setOnClickListener {
            replaceFragment(CompEditKataSandi())
        }
        btn_editPhone.setOnClickListener {
            replaceFragment(CompEditPhone())
        }

        btn_deactived.setOnClickListener {
            val sheet = ModalDeactivateAccount()
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager,
                    "DemoBottomSheetFragment"
                )
            }
        }

        return view
    }

    companion object {

    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }
}