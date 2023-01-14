package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ManageCV

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.`interface`.iUpdateMajor
import com.ciptakerjaarunika.kerjaloka.`interface`.iUpdateTitle
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentManageCvEditEducationPageBinding
import com.ciptakerjaarunika.kerjaloka.enum.Month
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.model.Data.Major
import com.ciptakerjaarunika.kerjaloka.model.Data.Title
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerEducationsRequest
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.manage_profile.iEditBasic
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit.*
import java.util.*


class fragment_manage_cv_edit_education_page(
    var data: JobseekerEducationsRequest?,
    val iRefreshData: iRefreshData
) : Fragment(), iEditBasic, iManageExp,
    iUpdateMajor, iUpdateTitle {
    private lateinit var binding: FragmentManageCvEditEducationPageBinding
    private var beginMonth: Int? =
        data?.educationBeginAt?.let { DateUtils().GetDateValueWithFormat(it, "MM").toInt() }
    private var endedMonth: Int? =
        data?.educationEndedAt?.let { DateUtils().GetDateValueWithFormat(it, "MM").toInt() }
    private var beginYear: Int? =
        data?.educationBeginAt?.let { DateUtils().GetDateValueWithFormat(it, "yyyy").toInt() }
    private var endedYear: Int? =
        data?.educationEndedAt?.let { DateUtils().GetDateValueWithFormat(it, "yyyy").toInt() }
    private var locations: List<LocationFilter> = listOf()
    private var majors: List<Major> = listOf()
    private var titles: List<Title> = listOf()
    var gpa: Double? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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

        if (data == null) {
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
                null
            )
            binding.mainToolbar.title = "Tambah Pendidikan"
        }
        binding.backBtn.setOnClickListener {
            back()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }
        binding.pilihBulanMulai.text =
            Month.values().find { month -> month.value == beginMonth }?.description
        binding.pilihBulanBerakhir.text =
            Month.values().find { month -> month.value == endedMonth }?.description
        binding.pilihTahunMulai.text = if (beginYear != null) beginYear.toString() else null
        binding.pilihTahunBerakhir.text = if (endedYear != null) endedYear.toString() else null
        binding.masukkanDeskripsiPendidikan.setText(data?.educationDescription)
        binding.masukkanSekolahUniversitas.setText(data?.educationSchool)
        data?.gpa?.let { binding.masukkanSkorGpa.setText(it.toString()) }

        DataAPI().GetTitles(context) { res ->
            if (res != null) {
                titles = res
                updateTitle(data?.educationTitleNo)
                binding.pilihGelar.setOnClickListener {
                    val sheet = ChooseTitle(data?.educationTitleNo, titles, this)
                    activity?.let { it1 ->
                        sheet.show(
                            it1.supportFragmentManager,
                            "DemoBottomSheetFragment"
                        )
                    }
                }
            }
        }


        DataAPI().GetMajors(context) {
            if (it != null) {
                majors = it
                updateMajor(data?.educationMajorNo)
                binding.pilihBidangStudi.setOnClickListener {
                    val sheet = ChooseMajor(data?.educationMajorNo, majors, this)
                    activity?.let { it1 ->
                        sheet.show(
                            it1.supportFragmentManager,
                            "DemoBottomSheetFragment"
                        )
                    }
                }
            }
        }

        binding.pilihLokasiSekolah.setOnClickListener {
            val sheet = EditCity(data?.educationCityNo, locations, this)
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager,
                    "DemoBottomSheetFragment"
                )
            }
        }

        binding.pilihBulanMulai.setOnClickListener {
            val sheet = ChooseMonth(if (beginMonth != null) beginMonth else null, "begin", this)
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager,
                    "DemoBottomSheetFragment"
                )
            }
        }
        binding.pilihBulanBerakhir.setOnClickListener {
            val sheet = ChooseMonth(if (endedMonth != null) endedMonth else null, "ended", this)
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager,
                    "DemoBottomSheetFragment"
                )
            }
        }

        binding.pilihTahunMulai.setOnClickListener {
            val sheet = ChooseYear("begin", if (beginYear != null) beginYear else null, this)
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager,
                    "DemoBottomSheetFragment"
                )
            }
        }
        binding.pilihTahunBerakhir.setOnClickListener {
            val sheet = ChooseYear("ended", if (endedYear != null) endedYear else null, this)
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager,
                    "DemoBottomSheetFragment"
                )
            }
        }
        binding.masukkanSkorGpa.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var value = binding.masukkanSkorGpa.text.toString()
                if (!value.isEmpty()) {
                    gpa = value.toDouble()
                } else {
                    gpa = 0.0
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.saveBtn.setOnClickListener {
            if (binding.masukkanSekolahUniversitas.text.isNullOrEmpty()) {
                showError("Nama Sekolah/Universitas tidak boleh kosong")
            } else if (data?.educationTitleNo == null || data?.educationTitleNo == 0) {
                showError("Gelar pendidikan tidak boleh kosong")
            } else if (data?.educationMajorNo == null || data?.educationMajorNo == 0) {
                showError("Bidang studi tidak boleh kosong")
            } else if (data?.educationCityNo == null || data?.educationCityNo == 0) {
                showError("Lokasi sekolah tidak boleh kosong")
            } else if (beginMonth == null) {
                showError("Bulan Mulai tidak boleh kosong")
            } else if (beginYear == null) {
                showError("Tahun Mulai tidak boleh kosong")
            } else if (endedMonth == null && endedYear != null) {
                showError("Bulan Berakhir tidak boleh kosong")
            } else if (endedYear == null && endedMonth != null) {
                showError("Tahun Mulai tidak boleh kosong")
            } else if (endedMonth != null && Date(endedYear!!, endedMonth!!, 1) < Date(
                    beginYear!!,
                    beginMonth!!,
                    1
                )
            ) {
                showError("Tanggal berakhir harus lebih besar dari tanggal mulai")
            } else if (binding.masukkanSkorGpa.text.isNullOrEmpty()) {
                showError("GPA tidak boleh kosong")
            } else if (data?.educationTitleNo == 1 && gpa!! >= 100) {
                showError("Nilai GPA Tidak Sah")
            } else if (data?.educationTitleNo != 1 && gpa!! >= 4.0) {
                showError("Nilai GPA Tidak Sah")
            } else {
                val endedAt = "${endedYear}-${String.format("%02d", endedMonth)}-01T00:00:00"
                val beginAt = "${beginYear}-${String.format("%02d", beginMonth)}-01T00:00:00"
                ManageProfileAPI().JobseekerManageEducation(
                    JobseekerEducationsRequest(
                        data?.jobseekerEducationNo,
                        SessionManager(context).user!!.userNo,
                        binding.masukkanSekolahUniversitas.text.toString(),
                        DateUtils().GetDateValueWithFormat(beginAt, "yyyy-MM-dd HH:mm"),
                        if (endedMonth == null) null else DateUtils().GetDateValueWithFormat(
                            endedAt,
                            "yyyy-MM-dd HH:mm"
                        ),
                        data?.educationMajorNo,
                        data?.educationTitleNo,
                        data?.educationCityNo,
                        gpa!!,
                        binding.masukkanDeskripsiPendidikan.text.toString()
                    ),
                    context
                ) {
                    if (it != null) {
                        if (it.code.toString() == "210") {
                            showError(if (data?.jobseekerEducationNo != null) "Berhasil mengubah data" else "Berhasil menambah data")
                            back()
                        } else {
                            showError(it.message)
                        }
                    } else {
                        showError("Terjadi kesalahan yang tidak diketahui")
                    }
                }
            }
        }
    }

    fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateGender(value: Char) {
    }

    private fun back() {
//        val fragmentTransaction = parentFragmentManager.beginTransaction()
//        fragmentTransaction?.replace(id, profilepage(0), "Profile Page")
//        fragmentTransaction?.commit()
        fragmentManager?.popBackStack()
        iRefreshData.refresh()
    }

    override fun updateCity(cityNo: Int?) {
        if (cityNo != null) {
            this.data?.educationCityNo = cityNo
        }
        var currentLocation = locations.find { loc -> loc.locationsNo == cityNo }
        if (currentLocation != null) {
            binding.pilihLokasiSekolah.text = "${currentLocation.city}, ${currentLocation.province}"
        }
    }

    override fun updateMonth(value: Int, type: String) {
        val monthTxt = Month.values().find { month -> month.value == value }?.description
        when (type) {
            "begin" -> {
                beginMonth = value
                binding.pilihBulanMulai.text = monthTxt
            }
            "ended" -> {
                endedMonth = value
                binding.pilihBulanBerakhir.text = monthTxt
            }
        }
    }

    override fun updateYear(value: Int, type: String) {
        when (type) {
            "begin" -> {
                beginYear = value
                binding.pilihTahunMulai.text = value.toString()
            }
            "ended" -> {
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
        if (currentItem != null) {
            binding.pilihBidangStudi.text = currentItem.majorName
        }
    }

    override fun updateTitle(value: Int?) {
        if (value != null) {
            this.data?.educationTitleNo = value
        }
        var currentItem = titles.find { item -> item.titleNo == value }
        if (currentItem != null) {
            binding.pilihGelar.text = currentItem.titleName
        }
    }
}