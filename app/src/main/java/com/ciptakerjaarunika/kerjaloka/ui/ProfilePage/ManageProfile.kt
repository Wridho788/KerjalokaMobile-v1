package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerProfile
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.EditAboutMe
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.EditAddInfo
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.EditBasicInfo
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils

class ManageProfile(val data : JobseekerProfile?) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.jsprofile_basic_info, container, false)
        val editBasic = view?.findViewById<TextView>(R.id.editBasicInfo)
        val btn_edAboutMe = view?.findViewById<TextView>(R.id.editAboutMe)
        val btn_edAddInfo = view?.findViewById<TextView>(R.id.editAddInfo)

        val txt_jsName = view.findViewById<TextView>(R.id.jsName)
        val txt_phoneNumber = view.findViewById<TextView>(R.id.jsPhone)
        val txt_KTP = view.findViewById<TextView>(R.id.jsKTP)
        val txt_gender = view.findViewById<TextView>(R.id.jsGender)
        val txt_alamat = view.findViewById<TextView>(R.id.jsAddress)
        val txt_dob = view.findViewById<TextView>(R.id.jsdob)
        val txt_city = view.findViewById<TextView>(R.id.jsCity)
        val txt_country = view.findViewById<TextView>(R.id.jsCountry)
        val txt_aboutMe = view.findViewById<TextView>(R.id.aboutMe)
        val txt_Marital = view.findViewById<TextView>(R.id.jsMarital)
        val txt_citizen = view.findViewById<TextView>(R.id.jsCitizen)
        val txt_pob = view.findViewById<TextView>(R.id.jsBirth)
        val txt_postalCode = view.findViewById<TextView>(R.id.jsPostCode)
        val txt_ethnic = view.findViewById<TextView>(R.id.jsEthnic)
        val txt_Religion = view.findViewById<TextView>(R.id.jsReligi)
        val txt_TeleID = view.findViewById<TextView>(R.id.jsTeleID)
        val txt_InstaID = view.findViewById<TextView>(R.id.jsInsta)

        txt_jsName.text = data?.jobseeker?.jobseekerName
        txt_phoneNumber.text = data?.users?.phone
        txt_KTP.text = data?.additionals?.ktp
        if(data?.jobseeker?.jobseekerGender == 'M') txt_gender.text = "Laki-Laki"
        else txt_gender.text = "Perempuan"
        txt_alamat.text = data?.additionals?.jobseekerCurrentAddress
        txt_dob.text = DateUtils().GetDateValueWithFormat(data?.jobseeker?.dateOfBirth, "dd MMMM yyyy")
        txt_city.text = data?.city?.cityName
        txt_country.text = data?.country?.countryName
        txt_aboutMe.text = data?.additionals?.jobseekerAbout
        txt_Marital.text = data?.marital?.maritalName
        txt_citizen.text = data?.resident?.residentName
        txt_pob.text = data?.additionals?.placeOfBirth
        txt_postalCode.text = data?.additionals?.postalCode
        txt_ethnic.text = data?.additionals?.ethnics
        txt_Religion.text = data?.religion?.religionName
        txt_TeleID.text = if(data?.additionals?.telegramId.isNullOrEmpty()) "-" else data?.additionals?.telegramId
        txt_InstaID.text = if(data?.additionals?.instagramId.isNullOrEmpty()) "-" else data?.additionals?.instagramId


        editBasic?.setOnClickListener {
          replaceFragment(EditBasicInfo(data))
        }
        btn_edAboutMe?.setOnClickListener{
            replaceFragment(EditAboutMe(data))
        }
        btn_edAddInfo?.setOnClickListener{
            replaceFragment(EditAddInfo(data))
        }
        return view
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }
}