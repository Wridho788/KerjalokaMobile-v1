package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anychart.ui.contextmenu.Item
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentProfileCvBinding
import com.ciptakerjaarunika.kerjaloka.model.Profile.*
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter.EduAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter.ExpAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter.LanguageAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ManageCV.PapikostickResult
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ManageCV.edit_kemampuan
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ManageCV.fragment_manage_cv_edit_education_page
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ManageCV.manage_cv_edit_experience_page
import com.ciptakerjaarunika.kerjaloka.viewmodel.ProfilePage.EditBahasa
import com.google.android.material.chip.Chip
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.perf.metrics.AddTrace
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class cvPage : Fragment(), iRefreshData, iCvPage {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var binding: FragmentProfileCvBinding
    private var loading = 4
    var pageHeight = 1120
    var pageWidth = 792
    lateinit var bmp: Bitmap
    lateinit var scaledbmp: Bitmap
    var PERMISSION_CODE = 101

    var jobseekername: String? = ""
    var jobseekerPhone: String? = ""
    var jobseekerdob: String? = ""
    var jobseekergender: String? = ""
    var jobseekeraddress: String? = ""
    var jobseekercityname: String? = ""
    var jobseekerstate: String? = ""
    var jobseekerktp: String? = ""
    var jobseekerAboutme: String? = ""
    var jobseekerMaritalstatus: String? = ""
    var jobseekerReligion: String? = ""
    var jobseekerEthnic: String? = ""
    var jobseekerResidence: String? = ""
    var jobseekerplaceofbirth: String? = ""
    var jobseekerpostalcode: String? = ""
    var jobseekertelegramid: String? = ""
    var jobseekerInstagramId: String? = ""

    var skills: List<JobseekerSkills>? = listOf()
    var experience: List<JobseekerExperiences>? = listOf()
    var education: List<JobseekerEducations>? = listOf()

    @AddTrace(name = "onCvPageTrace", enabled = true)
    class ItemCache {
        fun fetch(name: String): Item? {
            return null
        }
    }

    fun cvPageTrace() {
        val cache = ItemCache()
        val myTrace = Firebase.performance.newTrace("cv_page_trace")
        myTrace.start()
        val item = cache.fetch("item")
        if (item != null) {
            myTrace.incrementMetric("item_cache_hit", 1)
        } else {
            myTrace.incrementMetric("item_cache_miss", 1)
        }
        myTrace.stop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cvPageTrace()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileCvBinding.inflate(layoutInflater)
        val view = binding.root
        binding.swipeToRefresh.setColorSchemeColors(R.color.danger_500)
        binding.swipeToRefresh.setOnRefreshListener {
            GetData()
            binding.swipeToRefresh.isRefreshing = false
        }
        return view
    }

    fun checkPermissions(): Boolean {
        var readStoragePermission = ContextCompat.checkSelfPermission(
            context!!,
            READ_EXTERNAL_STORAGE
        )
        var writeStoragePermission = ContextCompat.checkSelfPermission(
            context!!, WRITE_EXTERNAL_STORAGE
        )
        return readStoragePermission == PackageManager.PERMISSION_GRANTED && writeStoragePermission == PackageManager.PERMISSION_GRANTED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GetData()
        bmp = BitmapFactory.decodeResource(resources, R.drawable.ic_company)
        scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false)
        binding.saveCV.setOnClickListener {
            if (checkPermissions()) {
                Toast.makeText(context, "Sedang Diproses..", Toast.LENGTH_SHORT).show()
                generateCvPdf()
            } else {
                requestPermission()
            }
        }
        ProfileAPI().JobseekerGetProfileData(context) { response ->
            if (response?.data != null) {
                Log.d("jobseekerGetProfileData", response.data.toString())
                this.jobseekername =
                    if (response.data.users.userFullname == null) "-" else response.data.users.userFullname
                this.jobseekerPhone =
                    if (response.data.users.phone == null) "-" else response.data.users.phone
                this.jobseekerktp =
                    if (response.data.additionals.ktp == null) "-" else response.data.additionals.ktp
                if (response.data.jobseeker.jobseekerGender.toString() == "M") {
                    this.jobseekergender = "Pria"
                } else this.jobseekergender = "Wanita"
                this.jobseekeraddress =
                    if (response.data.additionals.jobseekerCurrentAddress.isNullOrEmpty()) "-" else response.data.additionals.jobseekerCurrentAddress
                var dob = getDateValue(response.data.jobseeker.dateOfBirth)
                this.jobseekerdob = dob.toString()
                this.jobseekercityname =
                    if (response.data.city == null) "-" else response.data.city.cityName
                this.jobseekerstate =
                    if (response.data.country == null) "-" else response.data.country.countryName
                this.jobseekerAboutme =
                    if (response.data.additionals.jobseekerAbout == null) "-" else response.data.additionals.jobseekerAbout
                this.jobseekerMaritalstatus =
                    if (response.data.marital == null) "-" else response.data.marital.maritalName
                this.jobseekerReligion =
                    if (response.data.religion == null) "-" else response.data.religion.religionName
                this.jobseekerEthnic =
                    if (response.data.additionals.ethnics == null) "-" else response.data.additionals.ethnics
                this.jobseekerResidence =
                    if (response.data.resident == null) "-" else response.data.resident.residentName
                this.jobseekerplaceofbirth =
                    if (response.data.additionals.placeOfBirth == null) "-" else response.data.additionals.placeOfBirth
                this.jobseekerpostalcode =
                    if (response.data.additionals.postalCode == null) "-" else response.data.additionals.postalCode
                this.jobseekertelegramid =
                    if (response.data.additionals.telegramId == null) "-" else response.data.additionals.telegramId
                this.jobseekerInstagramId =
                    if (response.data.additionals.instagramId == null) "-" else response.data.additionals.instagramId
            }

        }
    }

    fun getDateValue(value: String): String? {
        var formatDate =
            LocalDateTime.parse(value).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        return formatDate
    }

    fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                activity?.let { it ->
                    ActivityCompat.requestPermissions(
                        it,
                        listOf(WRITE_EXTERNAL_STORAGE).toTypedArray(),
                        id + 101
                    )
                }
                generateCvPdf()
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.size > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    generateCvPdf()
                    Log.d("permission Granted", "Permission granted")
                } else {
                    Toast.makeText(context, "Permission Denied..", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun generateCvPdf() {
        var pdfDocument = PdfDocument()
        var paint = Paint()

        val bitmap = Bitmap.createBitmap(100, 50, Bitmap.Config.ARGB_8888)
        val canvasBitmap = Canvas(bitmap)
        canvasBitmap.drawColor(Color.RED)

        var header = Paint()
        var title = Paint()
        var subtitle = Paint()
        var text = Paint()
        var drawiLine = Paint()
        drawiLine.color = Color.GRAY
        drawiLine.style = Paint.Style.STROKE
        drawiLine.strokeWidth = 8F
        drawiLine.isAntiAlias = true
        val offset = 50
        drawiLine.color = Color.WHITE


        header.textAlign
        header.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        subtitle.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        text.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)

        header.isFakeBoldText = true
        title.isFakeBoldText = true
        header.color = ContextCompat.getColor(context!!, R.color.black)
        title.color = ContextCompat.getColor(context!!, R.color.black)
        subtitle.color = ContextCompat.getColor(context!!, R.color.black)
        text.color = ContextCompat.getColor(context!!, R.color.danger_700)

        header.textSize = 30F
        title.textSize = 20F
        subtitle.textSize = 15F
        text.textSize = 13F

//         page 1
        var myPageInfo: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        var myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)
        var canvas: Canvas = myPage.canvas
//        canvas.drawBitmap(scaledbmp, 56F, 40F, paint)
        canvas.drawText("Profile", 380F, 80F, header)
//        basic info
        canvas.drawText("Informasi Dasar", 50F, 140F, title)
        canvas.drawText("Nama", 50F, 210F, subtitle)
        canvas.drawText(jobseekername.toString(), 50F, 230F, text)

        canvas.drawText("Nomor Telepon", 50F, 280F, subtitle)
        canvas.drawText(jobseekerPhone.toString(), 50F, 300F, text)

        canvas.drawText("KTP", 50F, 350F, subtitle)
        canvas.drawText(jobseekerktp.toString(), 50F, 370F, text)

        canvas.drawText("Jenis Kelamin", 50F, 420F, subtitle)
        canvas.drawText(jobseekergender.toString(), 50F, 440F, text)

        canvas.drawText("Alamat", 400F, 210F, subtitle)
        canvas.drawText(jobseekeraddress.toString(), 400F, 230F, text)

        canvas.drawText("Tanggal Lahir", 400F, 280F, subtitle)
        canvas.drawText(jobseekerdob.toString(), 400F, 300F, text)

        canvas.drawText("Kota", 400F, 350F, subtitle)
        canvas.drawText(jobseekercityname.toString(), 400F, 370F, text)

        canvas.drawText("Negara", 400F, 420F, subtitle)
        canvas.drawText(jobseekerstate.toString(), 400F, 440F, text)
        canvas.drawLine(50f, 460F, 720F, 460f, paint)

//        about me
        canvas.drawText("Tentang Saya", 50F, 500F, title)
        canvas.drawText(
            "Beri tahu tentang dirimu supaya kamu lebih dikenal oleh perusahaan",
            50F,
            550F,
            subtitle
        )
        canvas.drawText(jobseekerAboutme.toString(), 50F, 580F, text)
        canvas.drawLine(50f, 600F, 720F, 600f, paint)

        // additional information
        canvas.drawText("Informasi Tambahan", 50F, 650F, title)
        canvas.drawText("Status Pernikahan", 50F, 700F, subtitle)
        canvas.drawText(jobseekerMaritalstatus.toString(), 50F, 720F, text)

        canvas.drawText("Kewarganegaraan", 50F, 800F, subtitle)
        canvas.drawText(jobseekerResidence.toString(), 50F, 820F, text)

        canvas.drawText("Tempat Lahir", 50F, 870F, subtitle)
        canvas.drawText(jobseekerplaceofbirth.toString(), 50F, 890F, text)

        canvas.drawText("Telegram ID", 50F, 940F, subtitle)
        canvas.drawText(jobseekertelegramid.toString(), 50F, 960F, text)

        canvas.drawText("Kode Pos", 400F, 730F, subtitle)
        canvas.drawText(jobseekerpostalcode.toString(), 400F, 750F, subtitle)

        canvas.drawText("Suku", 400F, 800F, subtitle)
        canvas.drawText(jobseekerEthnic.toString(), 400F, 820F, text)

        canvas.drawText("Agama", 400F, 870F, subtitle)
        canvas.drawText(jobseekerReligion.toString(), 400F, 890F, text)

        canvas.drawText("Instagram ID", 400F, 940F, subtitle)
        canvas.drawText(jobseekerInstagramId.toString(), 400F, 960F, text)

        pdfDocument.finishPage(myPage)
//      page 2
        var myPageInfo2: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        var myPage2: PdfDocument.Page = pdfDocument.startPage(myPageInfo2)
        var canvas2: Canvas = myPage2.canvas
//        CV
        canvas2.drawText("CV", 380F, 80F, header)
        canvas2.drawText("Skill", 50F, 140F, title)
        canvas2.drawText("Amateur", 50F, 190F, title)
        if (skills != null) {
            skills!!.forEach {
                if (it.scale == 1) {
                    canvas2.drawText(it.skillName, 50F, 210F, text)
                }
            }
        }
        canvas2.drawText("Beginner", 50F, 240F, title)
        if (skills != null) {
            skills!!.forEach {
                if (it.scale == 2) {
                    canvas2.drawText(it.skillName, 50F, 260F, text)
                }
            }
        }
        canvas2.drawText("Intermediate", 50F, 290F, title)
        if (skills != null) {
            skills!!.forEach {
                if (it.scale == 3) {
                    canvas2.drawText(it.skillName, 50F, 310F, text)
                }
            }
        }

        canvas2.drawText("Advance", 50F, 340F, title)
        if (skills != null) {
            skills!!.forEach {
                if (it.scale == 4) {
                    canvas2.drawText(it.skillName, 50F, 360F, text)
                }
            }
        }
        canvas2.drawText("Professional", 50F, 390F, title)
        if (skills != null) {
            skills!!.forEach {
                if (it.scale == 5) {
                    canvas2.drawText(it.skillName, 50F, 410F, text)
                }
            }
        }
        canvas2.drawText("Pengalaman", 50F, 470F, title)





        pdfDocument.finishPage(myPage2)
        val file =
            File(Environment.getExternalStorageDirectory().absolutePath + "/Download")
        var fileName = "CV.pdf"
        var files = File(file, fileName)
        try {
            pdfDocument.writeTo(FileOutputStream(files))
            Toast.makeText(context, "CV Berhasil Di Unduh", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("error writing", e.toString())
            Toast.makeText(context, "Fail to generate PDF file..", Toast.LENGTH_SHORT).show()
        }
        pdfDocument.close()
    }

    private fun GetData() {
        binding.chipGroup1.removeAllViews()
        binding.chipGroup2.removeAllViews()
        binding.chipGroup3.removeAllViews()
        binding.chipGroup4.removeAllViews()
        binding.chipGroup5.removeAllViews()
        ProfileAPI().GetJobseekerSkills(context) { skills ->
            loading -= 1
            LoadingDone()
            this.skills = skills?.data

            ProfileAPI().GetJobseekerExperiences(context) { experiences ->
                loading -= 1
                LoadingDone()
                if (experiences?.data?.size != 0) {
                    binding.layoutFreshgraduated.visibility = GONE
                    binding.layoutExperience.visibility = VISIBLE
                    binding.recycleExp.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = experiences?.data.let { ExpAdapter(it!!, this@cvPage) }
                    }
                } else {
                    binding.layoutExperience.visibility = GONE
                    binding.layoutFreshgraduated.visibility = VISIBLE
                    binding.checkFreshgraduated.isChecked = true

                    binding.checkFreshgraduated.setOnClickListener {
                        ProfileAPI().GetJobseekerFreshGraduated(context) {
                            if (it != null) {
                                binding.checkFreshgraduated.isChecked
                                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                                binding.layoutExperience.visibility = GONE
                            }
                        }
                    }
                }
            }

            ProfileAPI().GetJobseekerEducations(context) { educations ->
                loading -= 1
                LoadingDone()
                binding.recycleEdu.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = educations?.data?.let { EduAdapter(it, this@cvPage) }
                }

            }

            ProfileAPI().GetJobseekerLanguages(context) { languages ->
                loading -= 1
                LoadingDone()
                binding.recycleLang.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = languages?.data?.let { LanguageAdapter(false, it, null) }

                }

                if (skills?.data!!.size != 0) {
                    skills.data.forEach {
                        if (it.scale == 1) {
                            binding.skillLv1.visibility = VISIBLE
                            if (context != null) {
                                val skil1Chip = Chip(context)
                                skil1Chip.setChipBackgroundColorResource(R.color.danger_100)
                                skil1Chip.apply {
                                    textSize = 12f
                                    text = it.skillName
                                    isChipIconVisible = false
                                    isCloseIconVisible = false
                                    isClickable = true
                                    isCheckable = false
                                    view.apply {
                                        binding.chipGroup1.addView(skil1Chip as View)
                                    }
                                }
                            }
                        } else if (it.scale == 2) {
                            binding.skillLv2.visibility = VISIBLE
                            if (context != null) {
                                val skil2Chip = Chip(context)
                                skil2Chip.setChipBackgroundColorResource(R.color.danger_100)
                                skil2Chip.apply {
                                    textSize = 12f
                                    text = it.skillName
                                    isChipIconVisible = false
                                    isCloseIconVisible = false
                                    isClickable = true
                                    isCheckable = false
                                    view.apply {
                                        binding.chipGroup2.addView(skil2Chip as View)
                                    }
                                }
                            }
                        } else if (it.scale == 3) {
                            binding.skillLv3.visibility = VISIBLE
                            if (context != null) {
                                val skil3Chip = Chip(context)
                                skil3Chip.setChipBackgroundColorResource(R.color.danger_100)
                                skil3Chip.apply {
                                    textSize = 12f
                                    text = it.skillName
                                    isChipIconVisible = false
                                    isCloseIconVisible = false
                                    isClickable = true
                                    isCheckable = false
                                    view.apply {
                                        binding.chipGroup3.addView(skil3Chip as View)
                                    }
                                }
                            }
                        } else if (it.scale == 4) {
                            binding.skillLv4.visibility = VISIBLE
                            if (context != null) {
                                val skil4Chip = Chip(context)
                                skil4Chip.setChipBackgroundColorResource(R.color.danger_100)
                                skil4Chip.apply {
                                    textSize = 12f
                                    text = it.skillName
                                    isChipIconVisible = false
                                    isCloseIconVisible = false
                                    isClickable = true
                                    isCheckable = false
                                    view.apply {
                                        binding.chipGroup4.addView(skil4Chip as View)
                                    }
                                }
                            }
                        } else if (it.scale == 5) {
                            binding.skillLv5.visibility = VISIBLE
                            if (context != null) {
                                val skil5Chip = Chip(context)
                                skil5Chip.setChipBackgroundColorResource(R.color.danger_100)
                                skil5Chip.apply {
                                    textSize = 12f
                                    text = it.skillName
                                    isChipIconVisible = false
                                    isCloseIconVisible = false
                                    isClickable = true
                                    isCheckable = false
                                    view.apply {
                                        binding.chipGroup5.addView(skil5Chip as View)
                                    }
                                }
                            }
                        }
                    }
                } else {
                    binding.nullSkill.visibility = VISIBLE
                }

                binding.editSkill.setOnClickListener {
                    replaceFragment(edit_kemampuan(skills.data, this))
                }

                binding.seePapiResult.setOnClickListener {
                    ProfileAPI().GetPapiKostick(context) { res ->
                        if (res != null) {
                            val sheet = PapikostickResult(res.data)
                            activity?.let { it1 ->
                                sheet.show(
                                    it1.supportFragmentManager,
                                    "DemoBottomSheetFragment"
                                )
                            }
                        }
                    }
                }

                binding.addExp.setOnClickListener {
                    replaceFragment(manage_cv_edit_experience_page(null, this))
                }
                binding.addEdu.setOnClickListener {
                    replaceFragment(fragment_manage_cv_edit_education_page(null, this))
                }
                binding.addLang.setOnClickListener {
                    replaceFragment(
                        EditBahasa(
                            SessionManager(context).user!!.userNo,
                            languages?.data,
                            this
                        )
                    )
                }

            }
        }

        ProfileAPI().GetCvLink(context) {
            if (it?.code == 210) {
                binding.linkText.text = it.data
                binding.generateLink.text = "Revoke"
                binding.layoutBtnCopyLink.visibility = VISIBLE
                var clipboard =
                    requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("CV Link", it.data)

                binding.salinLink.setOnClickListener {
                    clipboard.setPrimaryClip(clipData)
                }
                binding.generateLink.setOnClickListener {
                    RevokedLink()
                }
            } else {
                binding.linkText.text = "Tidak Ada Link"
                binding.generateLink.text = "Generate"
                binding.layoutBtnCopyLink.visibility = GONE
                binding.generateLink.setOnClickListener {
                    GenerateLink()
                }
            }
        }
    }

    fun GenerateLink() {
        ProfileAPI().GetGeneratedLink(context) {
            if (it?.code == 210) {
                binding.linkText.text = it.data
                binding.generateLink.text = "Revoke"
                GetData()
            }
        }
    }

    fun RevokedLink() {
        ProfileAPI().GetRevokedLink(context) {
            if (it?.code == 210) {
                binding.linkText.text = "Tidak ada Link"
                binding.generateLink.text = "Generate"
                GetData()
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.addToBackStack("")
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }

    override fun editExp(data: JobseekerExperiences) {
        replaceFragment(
            manage_cv_edit_experience_page(
                JobseekerExperienceRequest(
                    data.experienceNo,
                    SessionManager(context).user!!.userNo,
                    data.experienceCityNo,
                    data.experienceCompanyName,
                    data.experienceCompanyNo,
                    data.experienceDescription,
                    data.experienceEndedAt,
                    data.experienceBeginAt, data.experienceJobTypeNo, data.experiencePosition,
                    data.experienceSalary
                ), this
            )
        )
    }

    override fun editEdu(data: JobseekerEducations) {
        replaceFragment(
            fragment_manage_cv_edit_education_page(
                JobseekerEducationsRequest(
                    data.jobseekerEducationNo,
                    SessionManager(context).user!!.userNo,
                    data.educationSchool,
                    data.educationBeginAt,
                    data.educationEndedAt,
                    data.educationMajorNo,
                    data.educationTitleNo,
                    data.educationCityNo,
                    data.gpa,
                    data.educationDescription
                ), this
            )
        )
    }

    override fun deleteExp(data: JobseekerExperiences) {
        AlertDialog.Builder(context)
            .setMessage("Yakin ingin menghapus '${data.experiencePosition}'?")
            .setTitle("Konfirmasi menghapus")
            .setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    ManageProfileAPI().JobseekerDeleteExperience(data.experienceNo, context) {
                        if (it != null) {
                            ProfileAPI().GetJobseekerExperiences(context) { experiences ->
                                binding.recycleExp.apply {
                                    layoutManager = LinearLayoutManager(activity)
                                    adapter = experiences?.data?.let { ExpAdapter(it, this@cvPage) }
                                }
                                Toast.makeText(activity, "Berhasil menghapus", Toast.LENGTH_SHORT)
                                    .show()
                                GetData()
                                dialog.dismiss()
                            }

                        }
                    }
                }
            })
            .setNegativeButton(android.R.string.cancel, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    dialog.dismiss()
                }
            }).create().show()
    }

    override fun deleteEducation(data: JobseekerEducations) {
        AlertDialog.Builder(context)
            .setMessage("Yakin ingin menghapus '${data.educationSchool}'?")
            .setTitle("Konfirmasi menghapus")
            .setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    ManageProfileAPI().JobseekerDeleteEducation(
                        data.jobseekerEducationNo,
                        context
                    ) {
                        if (it != null) {
                            ProfileAPI().GetJobseekerEducations(context) { edu ->
                                binding.recycleExp.apply {
                                    layoutManager = LinearLayoutManager(activity)
                                    adapter = edu?.data?.let { EduAdapter(it, this@cvPage) }
                                }
                                Toast.makeText(activity, "Berhasil menghapus", Toast.LENGTH_SHORT)
                                    .show()
                                dialog.dismiss()
                                GetData()
                            }

                        }
                    }
                }
            })
            .setNegativeButton(android.R.string.cancel, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    dialog.dismiss()
                }
            }).create().show()
    }

    fun LoadingDone() {
        if (loading == 0) {
            binding.spinner.visibility = GONE
            binding.contentContainer.visibility = VISIBLE
        }
    }

    override fun refresh() {
        GetData()
    }
}

interface iCvPage {
    fun editExp(data: JobseekerExperiences)
    fun editEdu(data: JobseekerEducations)
    fun deleteExp(data: JobseekerExperiences)
    fun deleteEducation(data: JobseekerEducations)
}