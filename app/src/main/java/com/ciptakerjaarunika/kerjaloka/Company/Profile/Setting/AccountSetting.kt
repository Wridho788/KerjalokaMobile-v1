package com.ciptakerjaarunika.kerjaloka.Company.Profile.Setting

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.core.view.isVisible
import com.airbnb.lottie.parser.ColorParser
import com.ciptakerjaarunika.kerjaloka.Company.Package.history_modal
import com.ciptakerjaarunika.kerjaloka.Company.Profile.*
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.api.users
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentAccountSettingBinding
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Global.ModalDeactivateAccount
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.ChooseScore
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson


class AccountSetting(var data: data?) : Fragment(), iRefreshData {
    private lateinit var binding : FragmentAccountSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        users().CompanyGetUserData(context) {
            binding.switchDiscoverable.isChecked = it?.data?.isDiscoverable!!
            binding.switchNewsLetter.isChecked = it?.data?.isNewsletter!!
            if (it?.data?.userGoogleId.isNullOrEmpty()){
                binding.connect.strokeColor= ColorStateList.valueOf(Color.parseColor("#FF6666"))
                binding.connect.setTextColor(ColorStateList.valueOf(Color.parseColor("#FF6666")))
                binding.connect.text="Hubungkan"
            }
            else{
                binding.connect.strokeColor= ColorStateList.valueOf(Color.parseColor("#FFDEDE"))
                binding.connect.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFDEDE")))
                binding.connect.isClickable=false
                binding.connect.text="Terkoneksi"
            }
            binding.switchDiscoverable.setOnClickListener{
                if (binding.switchDiscoverable.isChecked){
                    company_profile_api().discoverable(context){}
                }
                else{
                    company_profile_api().undiscoverable(context){}
                }
            }


            binding.switchNewsLetter.setOnClickListener { it1 ->
                val setNl = it?.data.isNewsletter
                company_profile_api().newsletter(setNl, context){}
            }
        }


        binding.btnLogout.setOnClickListener{
            ProfileAPI().Logout(SessionManager(context).device_token, context){
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            }
        }
        binding.editUsernameSetting.setOnClickListener{
            replaceFragment(CompEditUsername(this))
        }
        binding.editEmailProfileSetting.setOnClickListener {
            replaceFragment(CompEditEmail(this))
        }
        binding.editKataSandi.setOnClickListener {
            replaceFragment(CompEditKataSandi(this))
        }
        binding.editNomorTeleponSetting.setOnClickListener {
            replaceFragment(CompEditPhone(this))
        }

        binding.btnDeactivedAcc.setOnClickListener {
            val sheet = ModalDeactivateAccount()
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager,
                    "DemoBottomSheetFragment"
                )
            }
        }
        refresh()
    }

    companion object {

    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.addToBackStack("")
        fragmentTransaction?.commit()
    }

    override fun refresh() {
        company_profile_api().CompanyGetProfileData(context){
            binding.spinner.visibility = GONE
            binding.contentContainer.visibility = VISIBLE
            if (it != null) {
                data = it.data


                binding.profileUsername.text = data?.username
                binding.profilePhone.text = data?.phone
                binding.profileEmail.text = data?.email
                binding.compProfileAddress.text = data?.companyAddress

                users().CompanyGetUserData(context) {
                    binding.switchDiscoverable.isChecked = it?.data?.isDiscoverable!!
                    binding.switchNewsLetter.isChecked = it?.data?.isNewsletter!!
                    if (it?.data?.userGoogleId.isNullOrEmpty()){
                        binding.connect.strokeColor= ColorStateList.valueOf(Color.parseColor("#FF6666"))
                        binding.connect.setTextColor(ColorStateList.valueOf(Color.parseColor("#FF6666")))
                        binding.connect.text="Hubungkan"
                    }
                    else{
                        binding.connect.strokeColor= ColorStateList.valueOf(Color.parseColor("#FFDEDE"))
                        binding.connect.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFDEDE")))
                        binding.connect.isClickable=false
                        binding.connect.text="Terkoneksi"
                    }
                    binding.switchDiscoverable.setOnClickListener{
                        if (binding.switchDiscoverable.isChecked){
                            company_profile_api().discoverable(context){}
                        }
                        else{
                            company_profile_api().undiscoverable(context){}
                        }
                    }


                    binding.switchNewsLetter.setOnClickListener { it1 ->
                        val setNl = it?.data.isNewsletter
                        company_profile_api().newsletter(setNl, context){}
                    }
                }


                binding.btnLogout.setOnClickListener{
                    ProfileAPI().Logout(SessionManager(context).device_token, context){
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
                binding.editUsernameSetting.setOnClickListener{
                    replaceFragment(CompEditUsername(this))
                }
                binding.editEmailProfileSetting.setOnClickListener {
                    replaceFragment(CompEditEmail(this))
                }
                binding.editKataSandi.setOnClickListener {
                    replaceFragment(CompEditKataSandi(this))
                }
                binding.editNomorTeleponSetting.setOnClickListener {
                    replaceFragment(CompEditPhone(this))
                }

                binding.btnDeactivedAcc.setOnClickListener {
                    val sheet = ModalDeactivateAccount()
                    activity?.let { it1 ->
                        sheet.show(
                            it1.supportFragmentManager,
                            "DemoBottomSheetFragment"
                        )
                    }
                }
            }
        }
    }
}