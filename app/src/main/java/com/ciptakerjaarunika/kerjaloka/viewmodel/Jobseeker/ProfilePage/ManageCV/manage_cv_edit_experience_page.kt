package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ManageCV

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentManageCvEditExperiencePageBinding
import com.ciptakerjaarunika.kerjaloka.enum.Month
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.model.Data.JobTypeFilter
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerExperienceRequest
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit.ChooseMonth
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit.ChooseYear
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit.EditCity
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit.EditExpTypeJob
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.manage_profile.iEditBasic
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDateTime
import java.util.*

class manage_cv_edit_experience_page(
    var data: JobseekerExperienceRequest?, val iRefreshData: iRefreshData
) : Fragment(), iEditBasic, iManageExp {
    private lateinit var binding: FragmentManageCvEditExperiencePageBinding
    private var locations: List<LocationFilter> = listOf()
    private var jobTypes: List<JobTypeFilter> = listOf()

    @RequiresApi(Build.VERSION_CODES.O)
    private var beginMonth: Int? =
        data?.experienceBeginAt?.let { DateUtils().GetDateValueWithFormat(it, "MM").toInt() }

    @RequiresApi(Build.VERSION_CODES.O)
    private var endedMonth: Int? =
        data?.experienceEndedAt?.let { DateUtils().GetDateValueWithFormat(it, "MM").toInt() }

    @RequiresApi(Build.VERSION_CODES.O)

    private var beginYear: Int? =
        data?.experienceBeginAt?.let { DateUtils().GetDateValueWithFormat(it, "yyyy").toInt() }

    @RequiresApi(Build.VERSION_CODES.O)

    private var endedYear: Int? =
        data?.experienceEndedAt?.let { DateUtils().GetDateValueWithFormat(it, "yyyy").toInt() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageCvEditExperiencePageBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (data == null) {
            data = JobseekerExperienceRequest(
                null, null, null, null, null, null, null, null, null, null, null
            )
            binding.mainToolbar.title = "Tambah pengalaman"
        }
        binding.backBtn.setOnClickListener {
            back()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }

        var timeNow =
            DateUtils().GetDateValueWithFormat(LocalDateTime.now().toString(), "yyyy-MM-dd HH:mm")
        binding.masukkanJlhGaji.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(arg0: Editable) {
                binding.masukkanJlhGaji.removeTextChangedListener(this)

                try {
                    var originalString: String = arg0.toString()
                    if (originalString.contains(",")) {
                        originalString = originalString.replace(",".toRegex(), "")
                    }
                    val longval: Long = originalString.toLong()
                    val formatter: DecimalFormat =
                        NumberFormat.getInstance(Locale.US) as DecimalFormat
                    formatter.applyPattern("#,###,###,###")
                    val formattedString: String = formatter.format(longval)

                    //setting text after format to EditText
                    binding.masukkanJlhGaji.setText(formattedString)
                    binding.masukkanJlhGaji.setSelection(binding.masukkanJlhGaji.text.length)
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                }

                binding.masukkanJlhGaji.addTextChangedListener(this)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })

        DataAPI().GetJobTypes(context) { jobtypes ->
            if (jobtypes != null) {
                jobTypes = jobtypes
                data?.jobTypeNo?.let { updateJobType(it) }
                binding.pilihTipePekerjaan.setOnClickListener {
                    val sheet = EditExpTypeJob(data?.jobTypeNo, jobTypes, this)
                    activity?.let { it1 ->
                        sheet.show(
                            it1.supportFragmentManager, "DemoBottomSheetFragment"
                        )
                    }
                }
            }
        }
        binding.pilihBulanMulai.text =
            Month.values().find { month -> month.value == beginMonth }?.description
        binding.pilihBulanBerakhir.text =
            Month.values().find { month -> month.value == endedMonth }?.description
        binding.pilihTahunMulai.text = if (beginYear != null) beginYear.toString() else null
        binding.pilihTahunBerakhir.text = if (endedYear != null) endedYear.toString() else null
        if (data?.experienceSalary != null) {
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
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager, "DemoBottomSheetFragment"
                )
            }
        }

        binding.pilihBulanMulai.setOnClickListener {
            val sheet = ChooseMonth(if (beginMonth != null) beginMonth else null, "begin", this)
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager, "DemoBottomSheetFragment"
                )
            }
        }
        binding.pilihBulanBerakhir.setOnClickListener {
            val sheet = ChooseMonth(if (endedMonth != null) endedMonth else null, "ended", this)
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager, "DemoBottomSheetFragment"
                )
            }
        }

        binding.pilihTahunMulai.setOnClickListener {
            val sheet = ChooseYear("begin", if (beginYear != null) beginYear else null, this)
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager, "DemoBottomSheetFragment"
                )
            }
        }
        binding.pilihTahunBerakhir.setOnClickListener {
            val sheet = ChooseYear("ended", if (endedYear != null) endedYear else null, this)
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager, "DemoBottomSheetFragment"
                )
            }
        }

        binding.pilihPosisi.setText(data?.experiencePosition)
        data?.experienceCompanyName?.let { binding.pilihPerusahaan.setText(it) }
        binding.masukkanDeskrPekerjaan.setText(data?.experienceDescription)

        binding.saveBtn.setOnClickListener {
            if (binding.pilihPosisi.text.isNullOrEmpty()) {
                showError("Posisi pekerjaan tidak boleh kosong")
            } else if (binding.pilihPerusahaan.text.isNullOrEmpty()) {
                showError("Nama perusahaan tidak boleh kosong")
            } else if (data?.jobTypeNo == null || data?.jobTypeNo == 0) {
                showError("Tipe pekerjaan tidak boleh kosong")
            } else if (data?.experienceCityNo == null || data?.experienceCityNo == 0) {
                showError("Lokasi perusahaan tidak boleh kosong")
            } else if (beginMonth == null) {
                showError("Bulan Mulai tidak boleh kosong")
            } else if (beginYear == null) {
                showError("Tahun Mulai tidak boleh kosong")
            } else if (endedMonth == null && endedYear != null) {
                showError("Bulan Berakhir tidak boleh kosong")
            } else if (endedYear == null && endedMonth != null) {
                showError("Tahun Mulai tidak boleh kosong")
            } else if (endedMonth != null && Date(endedYear!!, endedMonth!!, 1) < Date(
                    beginYear!!, beginMonth!!, 1
                )
            ) {
                showError("Tanggal berakhir harus lebih besar dari tanggal mulai")
            } else if (beginYear != null && beginYear.toString() >= timeNow) {
                showError("Tanggal tidak boleh melebihi tanggal hari ini")
            } else if (binding.checkStillWorking.isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    binding.pilihBulanBerakhir.setAllowClickWhenDisabled(true)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    binding.pilihBulanBerakhir.setAllowClickWhenDisabled(true)
                }
                endedMonth = null
                endedYear = null
                var beginAt = "${beginYear}-${String.format("%02d", beginMonth)}-01T00:00:00"
                ManageProfileAPI().JobseekerManageExperience(
                    JobseekerExperienceRequest(
                        data?.jobseekerExperienceNo,
                        SessionManager(context).user!!.userNo,
                        data!!.experienceCityNo,
                        binding.pilihPerusahaan.text.toString(),
                        null,
                        binding.masukkanDeskrPekerjaan.text.toString(),
                        if (endedMonth == null) null else DateUtils().GetDateValueWithFormat(
                            null, "yyyy-MM-dd HH:mm"
                        ),
                        DateUtils().GetDateValueWithFormat(beginAt, "yyyy-MM-dd HH:mm"),
                        data!!.jobTypeNo,
                        binding.pilihPosisi.text.toString(),
                        if (binding.masukkanJlhGaji.text.isNullOrEmpty()) null else binding.masukkanJlhGaji.text.toString()
                            .toBigDecimal()
                    ), context
                ) {
                    if (it != null) {
                        if (it.code.toString() == "210") {
                            showError(if (data!!.jobseekerExperienceNo != null) "Berhasil mengubah data" else "Berhasil menambah data")
                            back()
                        } else {
                            showError(it.message)
                        }
                    } else {
                        showError("Terjadi kesalahan yang tidak diketahui")
                    }
                }
            } else if (endedMonth != null && endedMonth.toString() >= timeNow) {
                showError("Tanggal tidak boleh melebihi tanggal hari ini")
            } else {
                var endedAt = "${endedYear}-${String.format("%02d", endedMonth)}-01T00:00:00"
                var beginAt = "${beginYear}-${String.format("%02d", beginMonth)}-01T00:00:00"

                ManageProfileAPI().JobseekerManageExperience(
                    JobseekerExperienceRequest(
                        data?.jobseekerExperienceNo,
                        SessionManager(context).user!!.userNo,
                        data!!.experienceCityNo,
                        binding.pilihPerusahaan.text.toString(),
                        null,
                        binding.masukkanDeskrPekerjaan.text.toString(),
                        if (endedMonth == null) null else DateUtils().GetDateValueWithFormat(
                            endedAt, "yyyy-MM-dd HH:mm"
                        ),
                        DateUtils().GetDateValueWithFormat(beginAt, "yyyy-MM-dd HH:mm"),
                        data!!.jobTypeNo,
                        binding.pilihPosisi.text.toString(),
                        if (binding.masukkanJlhGaji.text.isNullOrEmpty()) null else binding.masukkanJlhGaji.text.toString()
                            .toBigDecimal()
                    ), context
                ) {
                    if (it != null) {
                        if (it.code.toString() == "210") {
                            showError(if (data!!.jobseekerExperienceNo != null) "Berhasil mengubah data" else "Berhasil menambah data")
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
        fragmentManager?.popBackStack()
        iRefreshData.refresh()
    }

    @SuppressLint("SetTextI18n")
    override fun updateCity(cityNo: Int?) {
        if (cityNo != null) {
            this.data?.experienceCityNo = cityNo
        }
        var currentLocation = locations.find { loc -> loc.locationsNo == cityNo }
        if (currentLocation != null) {
            binding.pilihLokasiPerusahaan.text =
                "${currentLocation.city}, ${currentLocation.province}"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
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

    override fun updateJobType(value: Int) {
        this.data?.jobTypeNo = value
        binding.pilihTipePekerjaan.text =
            jobTypes.find { type -> type.jobTypeNo == value }?.jobTypeName
    }
}

interface iManageExp {
    fun updateMonth(value: Int, type: String)
    fun updateYear(value: Int, type: String)
    fun updateJobType(value: Int)
}

