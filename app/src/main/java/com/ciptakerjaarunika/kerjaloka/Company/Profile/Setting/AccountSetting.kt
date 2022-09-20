package com.ciptakerjaarunika.kerjaloka.Company.Profile.Setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.ciptakerjaarunika.kerjaloka.Company.Profile.*
import com.ciptakerjaarunika.kerjaloka.R
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

//        val userL = ArrayList<user>()
//        val compL = ArrayList<company>()
//        val compAddL = ArrayList<compAdditional>()
//        val dataL = ArrayList<data>()
//        val countryL = Country()
//        val cityL = City()
//        val fieldL = Field()
//        val provinceL = Province()
//        val sizeL = Size()
//        val roleL = ArrayList<role>()

//        val c1 = company(
//            accountManager = null,
//            authorized = true,
//            authorizedUserNo = 0,
//            companyName = "TESTING",
//            companyNickName = null,
//            companyNo = 31,
//            userNo = 20211027141022
//        )
//        compL.add(c1)
//
//        val cAdd1 = compAdditional(
//            businessLicenseNumber = "2121212121212",
//            companyAddress = null,
//            companyCeo = "KAMI",
//            companyCityNo = 312,
//            companyCountryNo = 192,
//            companyDescription = "Testing",
//            companyNo = 20211027141022,
//            companyProvinceNo = 11,
//            companyTypeNo = null,
//            fieldNo = 4,
//            foundedAt = "1950-01-01T00:00:00",
//            ktp = "$2a$11\$hMpDzITmZhAMsy2YbYR5yOoMM5R56X4Fr10jK4U1GNp0mCG2Fsruy",
//            logo = "202110271410221246.jpg",
//            sizeNo = 6,
//        )
//        compAddL.add(cAdd1)
//
//        val dt1 = data(
//            city = cityL,
//            companyAddress = null,
//            companyCeo = "KAMI",
//            companyDescription = "Testing",
//            companyName = "TESTING",
//            country = countryL,
//            email = "reyhan@kerjaloka.com",
//            field = fieldL,
//            foundedAt = "1950-01-01T00:00:00",
//            logo = "202110271410221246.jpg",
//            phone = "082363153151",
//            province = provinceL,
//            size = sizeL,
//            userFullname = "reyhan@kerjaloka.com",
//            userNo = 20211027141022,
//            username = "reyhan@kerjaloka.com"
//        )
//        dataL.add(dt1)
//
//        val ctry = Country(
//            countryName = "Indonesia",
//            countryNo = 192
//        )
//        countryL.add(ctry)
//
//        val ct1 = City(
//            cityName = "Kabupaten Merauke",
//            cityNo = 312,
//            cityProvinceNo = 11
//        )
//        cityL.add(ct1)
//
//        val fld = Field(
//            fieldName = "Arts",
//            fieldNo = 4,
//            fieldParentNo = null
//        )
//        fieldL.add(fld)
//
//        val prv = Province(
//            provinceCountryNo = 192,
//            provinceName = "Papua",
//            provinceNo = 11
//        )
//        provinceL.add(prv)
//
//        val sz = Size(
//            sizeName = "500+",
//            sizeNo = 6
//        )
//        sizeL.add(sz)
//
//        val usr1 = user(
//            company = compL,
//            companyAdditional = compAddL,
//            createdBy = 0,
//            createdOn = "2021-10-27T21:10:22",
//            deactivated = false,
//            email = "reyhan@kerjaloka.com",
//            emergencyPhone = null,
//            isDiscoverable = false,
//            isNewsletter = true,
//            jobseekerAdditional = null,
//            jobseekers = null,
//            phone = "082363153151",
//            roleNo = 2,
//            rolePrevileges = roleL,
//            suspended = false,
//            userFullname = "reyhan@kerjaloka.com",
//            userGoogleId = "113351807463143838932",
//            userNo = 20211027141022,
//            username = "reyhan@kerjaloka.com"
//        )
//        userL.add(usr1)

        val txt_usrName = view.findViewById<TextView>(R.id.profile_username)
        val txt_phone = view.findViewById<TextView>(R.id.profile_nomor_telepon)
        val txt_email = view.findViewById<TextView>(R.id.profile_email)
        val txt_addrees = view.findViewById<TextView>(R.id.comp_profile_address)
        val discover = view.findViewById<Switch>(R.id.switchDiscoverable)
        val newsletter = view.findViewById<Switch>(R.id.switchNewsLetter)

        txt_usrName.text = data?.username
        txt_phone.text = data?.phone
        txt_email.text = data?.email
        txt_addrees.text = data?.companyAddress
//        discover.isChecked = userL[0].isDiscoverable
//        newsletter.isChecked = userL[0].isNewsletter

        btn_editUsername.setOnClickListener{
            replaceFragment(CompEditUsername())
        }
        btn_editEmail.setOnClickListener{
            replaceFragment(CompEditEmail())
        }
        btn_editPswd.setOnClickListener{
            replaceFragment(CompEditKataSandi())
        }
        btn_editPhone.setOnClickListener{
            replaceFragment(CompEditPhone())
        }

        btn_deactived.setOnClickListener {
            val sheet = ModalDeactivateAccount()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }

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