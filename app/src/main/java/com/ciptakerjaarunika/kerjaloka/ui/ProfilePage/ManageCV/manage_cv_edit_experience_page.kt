package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentManageCvEditExperiencePageBinding
import com.ciptakerjaarunika.kerjaloka.enum.Month
import com.ciptakerjaarunika.kerjaloka.model.Data.JobTypeFilter
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerExperienceRequest
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.ChooseMonth
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.ChooseYear
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditCity
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditExpTypeJob
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.iEditBasic
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils
import java.util.*


class manage_cv_edit_experience_page(var data : JobseekerExperienceRequest?, val iRefreshData: iRefreshData) : Fragment(), iEditBasic, iManageExp {
        private  lateinit var binding : FragmentManageCvEditExperiencePageBinding
        private var locations : List<LocationFilter> = listOf()
        private var jobTypes : List<JobTypeFilter> = listOf()
        private var beginMonth : Int? = data?.experienceBeginAt?.let { DateUtils().GetDateValueWithFormat(it, "MM").toInt() }
        private var endedMonth : Int? = data?.experienceEndedAt?.let { DateUtils().GetDateValueWithFormat(it, "MM").toInt()}
        private var beginYear : Int? = data?.experienceBeginAt?.let { DateUtils().GetDateValueWithFormat(it, "yyyy").toInt()}
        private var endedYear : Int? = data?.experienceEndedAt?.let { DateUtils().GetDateValueWithFormat(it, "yyyy").toInt()}

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
        if(data == null) {
            data = JobseekerExperienceRequest(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
                ,null
                ,null,
                null)
            binding.mainToolbar.title = "Tambah pengalaman"
        }
        binding.backBtn.setOnClickListener{
            back()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }

        binding.masukkanJlhGaji.addTextChangedListener(

        )

        DataAPI().GetJobTypes(context){ jobtypes ->
            if (jobtypes != null) {
                jobTypes = jobtypes
                data?.jobTypeNo?.let { updateJobType(it) }
                binding.pilihTipePekerjaan.setOnClickListener {
                    val sheet = EditExpTypeJob(data?.jobTypeNo, jobTypes, this)
                    activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
                }
            }
        }
        binding.pilihBulanMulai.text = Month.values().find { month -> month.value == beginMonth  }?.description
        binding.pilihBulanBerakhir.text = Month.values().find { month -> month.value == endedMonth  }?.description
        binding.pilihTahunMulai.text = if(beginYear!= null) beginYear.toString() else null
        binding.pilihTahunBerakhir.text = if(endedYear!= null) endedYear.toString() else null
        if(data?.experienceSalary != null) {
            binding.masukkanJlhGaji.setText(data?.experienceSalary.toString())
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

        binding.pilihPosisi.setText(data?.experiencePosition)
        data?.experienceCompanyName?.let { binding.pilihPerusahaan.setText(it) }
        binding.masukkanDeskrPekerjaan.setText(data?.experienceDescription)

        binding.saveBtn.setOnClickListener {
            if(binding.pilihPosisi.text.isNullOrEmpty()){
                showError("Posisi pekerjaan tidak boleh kosong")
            }
            else if(binding.pilihPerusahaan.text.isNullOrEmpty()){
                showError("Nama perusahaan tidak boleh kosong")
            }
            else if(data?.jobTypeNo == null || data?.jobTypeNo == 0){
                showError("Tipe pekerjaan tidak boleh kosong")
            }
            else if(data?.experienceCityNo == null || data?.experienceCityNo == 0){
                showError("Lokasi perusahaan tidak boleh kosong")
            }
            else if(beginMonth == null){
                showError("Bulan Mulai tidak boleh kosong")
            }
            else if(beginYear == null){
                showError("Tahun Mulai tidak boleh kosong")
            }
            else if(endedMonth == null && endedYear != null){
                showError("Bulan Berakhir tidak boleh kosong")
            }
            else if(endedYear == null && endedMonth != null){
                showError("Tahun Mulai tidak boleh kosong")
            }
            else if(endedMonth != null && Date(endedYear!!, endedMonth!!, 1) < Date(beginYear!!, beginMonth!!, 1)){
                showError("Tanggal berakhir harus lebih besar dari tanggal mulai")
            }
//            else if(binding.masukkanJlhGaji.text.isNullOrEmpty()){
//                showError("Gaji tidak boleh kosong")
//            }
            else{
                val endedAt =  "${endedYear}-${String.format("%02d",endedMonth)}-01T00:00:00"
                val beginAt =  "${beginYear}-${String.format("%02d",beginMonth)}-01T00:00:00"
                ManageProfileAPI().JobseekerManageExperience(
                    JobseekerExperienceRequest(
                        data?.jobseekerExperienceNo,
                        SessionManager(context).user!!.userNo,
                        data!!.experienceCityNo,
                        binding.pilihPerusahaan.text.toString(),
                        null,
                        binding.masukkanDeskrPekerjaan.text.toString(),
                        if(endedMonth == null) null else DateUtils().GetDateValueWithFormat(endedAt, "yyyy-MM-dd HH:mm"),
                        DateUtils().GetDateValueWithFormat(beginAt, "yyyy-MM-dd HH:mm"),
                        data!!.jobTypeNo,
                        binding.pilihPosisi.text.toString(),
                        if(binding.masukkanJlhGaji.text.isNullOrEmpty()) null else binding.masukkanJlhGaji.text.toString().toBigDecimal()
                    ),
                    context
                ){
                    if (it != null) {
                        if(it.code.toString() == "210"){
                            showError(if(data!!.jobseekerExperienceNo != null) "Berhasil mengubah data" else "Berhasil menambah data")
                            back()
                        }
                        else{
                            showError(it.message)
                        }
                    }
                    else{
                        showError("Terjadi kesalahan yang tidak diketahui")
                    }
                }
            }
        }
    }

//    private fun formatRupiah(number: Double): String? {
//        val localeID = Locale("in", "ID")
//        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
//        return formatRupiah.format(number)
//    }

    fun showError(message : String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateGender(value: Char) {
    }
    private fun back(){
        fragmentManager?.popBackStack()
        iRefreshData.refresh()
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

    override fun updateJobType(value: Int) {
        this.data?.jobTypeNo = value
        binding.pilihTipePekerjaan.text = jobTypes.find { type-> type.jobTypeNo == value }?.jobTypeName
    }
}

interface iManageExp{
    fun updateMonth(value : Int, type : String)
    fun updateYear(value : Int, type : String)
    fun updateJobType(value: Int)
}

