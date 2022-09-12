package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.*
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.EditAboutMe
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.EditAddInfo
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.EditBasicInfo
import org.w3c.dom.Text


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ManageProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class ManageProfile : Fragment() {
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
        var data = js_profile()
        var addInfo = add_Info()
        var city = city()
        var country = country()
        var marital = marital()
        var province = province()
        var religi = religion()
        var resident = resident()
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

        txt_jsName.text = data.jobseekerName
        txt_phoneNumber.text = addInfo.jobseekerNo.toString()
        txt_KTP.text = addInfo.ktp
        if(data.jsGender=="M"){
            txt_gender.text = "Laki-Laki"
        }
        else{
            txt_gender.text = "Perempuan"
        }
        txt_alamat.text = addInfo.jobseekerCurrentAddress
        txt_dob.text = data.dateOfBirth
        txt_city.text = city.cityName
        txt_country.text = country.countryName
        txt_aboutMe.text = addInfo.jobseekerAbout
        txt_Marital.text = marital.maritalName
        txt_citizen.text = resident.residentName
        txt_pob.text = addInfo.placeOfBirth
        txt_postalCode.text = addInfo.postalCode
        txt_ethnic.text = addInfo.ethnics
        txt_Religion.text = religi.religionName
        txt_TeleID.text = addInfo.telegramId
        txt_InstaID.text = addInfo.instagramId


        editBasic?.setOnClickListener {
          replaceFragment(EditBasicInfo())
        }
        btn_edAboutMe?.setOnClickListener{
            replaceFragment(EditAboutMe())
        }
        btn_edAddInfo?.setOnClickListener{
            replaceFragment(EditAddInfo())
        }

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ManageProfile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ManageProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }
}