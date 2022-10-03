package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.BottomSheetMajorJob
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.iUpdateMajor
import com.ciptakerjaarunika.kerjaloka.`interface`.iUpdateTitle
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.Majors
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.*
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentManageCvEditEducationPageBinding
import com.ciptakerjaarunika.kerjaloka.enum.Month
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.model.Data.Major
import com.ciptakerjaarunika.kerjaloka.model.Data.Title
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerEducations
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerEducationsRequest
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerExperienceRequest
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.iEditBasic
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.profilepage
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils


class fragment_manage_cv_edit_education_page(var data : JobseekerEducationsRequest?) : Fragment(), iEditBasic, iManageExp,
    iUpdateMajor, iUpdateTitle {
    private lateinit var binding : FragmentManageCvEditEducationPageBinding
    private var beginMonth : Int? = data?.educationBeginAt?.let { DateUtils().GetDateValueWithFormat(it, "MM").toInt() }
    private var endedMonth : Int? = data?.educationEndedAt?.let { DateUtils().GetDateValueWithFormat(it, "MM").toInt()}
    private var beginYear : Int? = data?.educationBeginAt?.let { DateUtils().GetDateValueWithFormat(it, "yyyy").toInt()}
    private var endedYear : Int? = data?.educationEndedAt?.let { DateUtils().GetDateValueWithFormat(it, "yyyy").toInt()}
    private var locations : List<LocationFilter> = listOf()
    private var majors : List<Major> = listOf()
    private var titles : List<Title> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(data == null){
            data = JobseekerEducationsRequest(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)
        }

        DataAPI().GetLocations(context) { res ->
            if (res != null) {
                locations = res
                updateCity(data?.educationCityNo)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageCvEditEducationPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(data == null) {
            binding.mainToolbar.title = "Tambah Pendidikan"
        }
        binding.backBtn.setOnClickListener{
            back()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }
        binding.pilihBulanMulai.text = Month.values().find { month -> month.value == beginMonth  }?.description
        binding.pilihBulanBerakhir.text = Month.values().find { month -> month.value == endedMonth  }?.description
        binding.pilihTahunMulai.text = beginYear.toString()
        binding.pilihTahunBerakhir.text = if(endedYear!= null) endedYear.toString() else null

        binding.pilihGelar.setOnClickListener {
            val sheet = ChooseTitle(data?.educationTitleNo, this)
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }

        DataAPI().GetMajors(context) {
            if (it != null) {
                majors = it
                binding.pilihBidangStudi.setOnClickListener {
                    val sheet = ChooseMajor(data?.educationMajorNo,majors, this)
                    activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
                }
            }
        }

        binding.pilihLokasiSekolah.setOnClickListener {
            val sheet = EditCity(data?.educationCityNo, locations, this)
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }

        binding.pilihBulanMulai.setOnClickListener {
            val sheet = ChooseMonth(if(beginMonth != null) beginMonth else null, "begin", this)
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        binding.pilihBulanBerakhir.setOnClickListener {
            val sheet = ChooseMonth(if(endedMonth != null) endedMonth else null, "ended", this)
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
            this.data?.educationCityNo = cityNo
        }
        var currentLocation = locations.find { loc-> loc.locationsNo == cityNo }
        if(currentLocation != null){
            binding.pilihLokasiSekolah.setText("${currentLocation.city}, ${currentLocation.province}")
        }
    }

    override fun updateMonth(value: Int, type: String) {
        val monthTxt = Month.values().find { month-> month.value == value }?.description
        when (type){
            "begin" ->{
                beginMonth = value
                binding.pilihBulanMulai.text = monthTxt
            }
            "ended"->{
                endedMonth = value
                binding.pilihBulanBerakhir.text = monthTxt
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

    override fun updateJobType(value: Int) {}


    override fun updateMajor(value: Int?) {
        if (value != null) {
            this.data?.educationMajorNo = value
        }
        var currentItem = majors.find { item -> item.majorNo == value }
        if(currentItem != null){
            binding.pilihBidangStudi.text = currentItem.majorName
        }
    }

    override fun updateTitle(value: Int?) {
        if (value != null) {
            this.data?.educationTitleNo = value
        }
        var currentItem = titles.find { item -> item.titleNo == value }
        if(currentItem != null){
            binding.pilihGelar.text = currentItem.titleName
        }
    }
}