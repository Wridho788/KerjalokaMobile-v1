package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile

import android.R
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentEditBasicInfoBinding
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.model.Interview.returnUploadFile
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerProfile
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditCity
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditGender
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.profilepage
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils
import com.google.android.material.datepicker.*
import com.qingmei2.rximagepicker_extension.utils.PathUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class EditBasicInfo(val data : JobseekerProfile?) : Fragment(), iEditBasic {
    private var locations :List<LocationFilter> = listOf()
    private lateinit var binding : FragmentEditBasicInfoBinding
    private var gender = data?.jobseeker?.jobseekerGender
    private var cityNo = data?.additionals?.jobseekerCityNo
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    private var photo : MultipartBody.Part? = null
    private val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private var dateValue : Date? = if (data?.jobseeker?.dateOfBirth == null) null
                                    else DateUtils().GetDateValue(data?.jobseeker?.dateOfBirth)
    private var date : String? = if (data?.jobseeker?.dateOfBirth == null) null
                                    else DateUtils().GetDateValueWithFormat(data?.jobseeker?.dateOfBirth, "yyyy-MM-dd HH:mm")



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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                val data = it.data
                val fileUri: Uri? = data?.data
                val pathName =
                    fileUri?.let { it1 -> context?.let { it2 -> PathUtils.getPath(it2, it1) } }

                val file = File(pathName ?: "")
                val requestFile: RequestBody =
                    file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
                binding.profileImg.setImageBitmap(myBitmap)
                photo = MultipartBody.Part.createFormData("photo", file.name, requestFile)
            }
        }
        binding.profileImg.setOnClickListener{
            updatePhoto()
        }
        binding.changePhotoTxt.setOnClickListener{
            updatePhoto()
        }

        binding.backBtn.setOnClickListener{
            back()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }
        Glide.with(context!!)
            .load(config().portAddress + "/photo/Profile/" + data?.additionals?.Photo).into(binding.profileImg)

        binding.jsName.setText(data?.jobseeker?.jobseekerName)
        binding.jsKTP.setText(data?.additionals?.ktp)
        binding.jsGender.setText(
            if(gender == 'M') "Laki-laki" else "Perempuan")
        binding.jsAddress.setText(data?.additionals?.jobseekerCurrentAddress)

        if(data?.jobseeker?.dateOfBirth != null){
            binding.jsBirthDay.setText(DateUtils().GetDateValueWithFormat(data.jobseeker.dateOfBirth, "dd MMMM yyyy"))
        }
        binding.jsBirthDay.setOnClickListener{
                val datePicker = MaterialDatePicker
                    .Builder
                    .datePicker()
                    .setTitleText("Pilih tanggal lahir")
                    .setCalendarConstraints(calendarConstraints).build()

                datePicker.show(requireActivity().supportFragmentManager, "materialDatePicker")

                datePicker.addOnPositiveButtonClickListener {
                    val dates = Date(it)
                    dateValue = dates;

                    date = SimpleDateFormat("yyyy-MM-dd HH:mm").format(dates)
                    binding.jsBirthDay.setText(SimpleDateFormat("dd MMMM yyyy").format(dates))
                }
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
        binding.btnSaveBasic.setOnClickListener{
            if(photo != null) { ManageProfileAPI().UploadPhoto(context, photo!!){ resUpload ->
                if(resUpload?.code == 210){
                    updateBasic()
                }
                else{
                    Toast.makeText(activity, resUpload?.message, Toast.LENGTH_SHORT).show()
                }
            }
            }
            else{
                updateBasic()
            }
        }
    }

    fun updatePhoto(){
        var intent = Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        val requestIntent = Intent.createChooser(intent, "Choose a Image");
        activityResultLauncher.launch(requestIntent)
    }
    fun getLongAsDate(year: Int, month: Int, date: Int): Long {
        val calendar: Calendar = GregorianCalendar()
        calendar[Calendar.DAY_OF_MONTH] = date
        calendar[Calendar.MONTH] = month - 1
        calendar[Calendar.YEAR] = year
        return calendar.timeInMillis
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private val calendarConstraints = CalendarConstraints.Builder().setOpenAt(
        dateValue?.time
            ?: sdf.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).time
    ).build()

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
        fragmentTransaction?.replace(id, profilepage(0), "Profile Page")
        fragmentTransaction?.commit()
    }
    private fun updateBasic(){
        ManageProfileAPI().EditBasicInfo(
            ManageProfileAPI.editBasicInfoRequest(
                binding.jsName.text.toString(),
                binding.jsKTP.text.toString(),
                gender,
                binding.jsAddress.text.toString(),
                date,
                cityNo
            ), context
        ){
            if(it != null) {
                Toast.makeText(activity, "Berhasil mengubah data", Toast.LENGTH_SHORT).show()
                back()
            }
            else{
                Toast.makeText(activity, "Terjadi kesalahan yang tidak diketahui", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
interface iEditBasic{
    fun updateGender(value :Char)
    fun updateCity(cityNo :Int?)
}