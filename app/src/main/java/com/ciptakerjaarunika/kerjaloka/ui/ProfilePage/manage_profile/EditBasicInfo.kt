package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.Company.Profile.city
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentEditBasicInfoBinding
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerProfile
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditCity
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditGender
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.profilepage
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils

class EditBasicInfo(val data : JobseekerProfile?) : Fragment(), iEditBasic {
    private var locations :List<LocationFilter> = listOf()
    private lateinit var binding : FragmentEditBasicInfoBinding
    private var gender = data?.jobseeker?.jobseekerGender
    private var cityNo = data?.additionals?.jobseekerCityNo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = FragmentEditBasicInfoBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener{
            back()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }

        context?.let { Glide.with(it)
            .load(config().portAddress + "/photo/Profile/" + data?.additionals?.Photo).into(binding.profileImg) }

        binding.jsName.setText(data?.jobseeker?.jobseekerName)
        binding.jsKTP.setText(data?.additionals?.ktp)
        binding.jsGender.setText(
            if(gender == 'M') "Laki-laki" else "Perempuan")
        binding.jsAddress.setText(data?.additionals?.jobseekerCurrentAddress)

        if(data?.jobseeker?.dateOfBirth != null){
            binding.jsBirthDay.setText(DateUtils().GetDateValueWithFormat(data.jobseeker.dateOfBirth, "dd MMMM yyyy"))
        }

        DataAPI().GetLocations(context) { res ->
            if (res != null) {
                locations = res
                updateCity(cityNo)
            }
        }

        binding.jsGender.setOnClickListener {
            val sheet = EditGender(gender, this)
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }

        binding.jsCity.setOnClickListener {
            val sheet = EditCity(cityNo, locations, this)
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
    }

    override fun updateGender(value: Char) {
        this.gender = value
        binding.jsGender.setText(
            if(gender == 'M') "Laki-laki" else "Perempuan")
    }

    override fun updateCity(cityNo: Int?) {
        this.cityNo = cityNo
        var currentLocation = locations.find { loc-> loc.locationsNo == cityNo }
        if(currentLocation != null){
            binding.jsCity.setText("${currentLocation.city}, ${currentLocation.province}")
        }
    }
    private fun back(){
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction?.replace(id, profilepage(), "Profile Page")
        fragmentTransaction?.commit()
    }
}
interface iEditBasic{
    fun updateGender(value :Char)
    fun updateCity(cityNo :Int?)
}