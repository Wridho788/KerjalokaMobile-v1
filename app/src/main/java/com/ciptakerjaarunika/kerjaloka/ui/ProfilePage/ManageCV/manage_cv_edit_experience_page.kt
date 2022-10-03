package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentManageCvEditExperiencePageBinding
import com.ciptakerjaarunika.kerjaloka.enum.Month
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerExperiences
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EditExp_TypeJob
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.*
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.iEditBasic
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.profilepage
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils
import java.util.*

class manage_cv_edit_experience_page(var data : JobseekerExperiences?) : Fragment(), iEditBasic, iManageExp {
        private  lateinit var binding : FragmentManageCvEditExperiencePageBinding
        private var locations : List<LocationFilter> = listOf()
        private var beginMonth : Int? = data?.experienceBeginAt?.let { DateUtils().GetDateValue(it).month }
        private var endedMonth : Int? = data?.experienceEndedAt?.let { DateUtils().GetDateValue(it).month }
        private var beginYear : Int? = data?.experienceBeginAt?.let { DateUtils().GetDateValue(it).year }
        private var endedYear : Int? = data?.experienceEndedAt?.let { DateUtils().GetDateValue(it).year }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentManageCvEditExperiencePageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener{
            back()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }

        binding.pilihTipePekerjaan.setOnClickListener {
            val sheet = EditExpTypeJob(data?.experienceJobTypeNo)
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        DataAPI().GetLocations(context) { res ->
            if (res != null) {
                locations = res
                updateCity(data?.experienceCityNo)
            }
        }
        binding.pilihLokasiPerusahaan.setOnClickListener {
            val sheet = EditCity(data?.experienceCityNo, locations, this)
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        binding.pilihBulanBerakhir.setOnClickListener {
            val sheet = ChooseMonth(if(endedMonth != null) endedMonth else null)
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        binding.pilihBulanMulai.setOnClickListener {
            val sheet = ChooseMonth(if(beginMonth != null) beginMonth else null)
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        binding.pilihTahunMulai.setOnClickListener {
            val sheet = ChooseYear("begin", if(beginYear != null) beginYear else null, this)
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        binding.pilihTahunBerakhir.setOnClickListener {
            val sheet = ChooseYear("ended",if(endedYear != null) endedYear else null, this)
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
    }

    override fun updateGender(value: Char) {
    }
    private fun back(){
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction?.replace(id, profilepage(0), "Profile Page")
        fragmentTransaction?.commit()
    }

    override fun updateCity(cityNo: Int?) {
        if (cityNo != null) {
            this.data?.experienceCityNo = cityNo
        }
        var currentLocation = locations.find { loc-> loc.locationsNo == cityNo }
        if(currentLocation != null){
            binding.pilihLokasiPerusahaan.setText("${currentLocation.city}, ${currentLocation.province}")
        }
    }

    override fun updateJobTypeNo(value: Int) {
        this.data?.experienceJobTypeNo = value
    }

    override fun updateMonth(value: Int, type: String) {
        when (type){
            "begin" ->{
                beginMonth = value
                binding.pilihBulanMulai.text = (value as Month).description
            }
            "ended"->{
                endedMonth = value
                binding.pilihBulanBerakhir.text = (value as Month).description
            }
        }
    }

    override fun updateYear(value: Int, type: String) {
        when (type){
            "begin" ->{
                beginYear = value
                binding.pilihTahunMulai.text = value.toString()
            }
            "ended"->{
                endedYear = value
                binding.pilihTahunBerakhir.text = value.toString()
            }
        }
    }
}

interface iManageExp{
    fun updateJobTypeNo(value : Int)
    fun updateMonth(value : Int, type : String)
    fun updateYear(value : Int, type : String)
}

