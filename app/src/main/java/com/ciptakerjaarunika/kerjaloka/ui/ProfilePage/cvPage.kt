package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
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
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentProfileCvBinding
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerEducations
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerEducationsRequest
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerExperienceRequest
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerExperiences
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EduAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.ExpAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.LanguageAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.PapikostickResult
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.edit_kemampuan
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.fragment_manage_cv_edit_education_page
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.manage_cv_edit_experience_page
import com.google.android.material.chip.Chip
import java.io.File
import java.io.FileOutputStream

class cvPage : Fragment(), iRefreshData, iCvPage {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var binding: FragmentProfileCvBinding
    private var loading = 4


    var pageHeight = 1120
    var pageWidth = 792

    lateinit var bmp: Bitmap
    lateinit var scaledbmp: Bitmap
    var PERMISSION_CODE = 101

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
        var writeStoragePermission = ContextCompat.checkSelfPermission(
            context!!,
            WRITE_EXTERNAL_STORAGE
        )
        var readStoragePermission = ContextCompat.checkSelfPermission(
            context!!,
            READ_EXTERNAL_STORAGE
        )
        return writeStoragePermission == PackageManager.PERMISSION_GRANTED
                && readStoragePermission == PackageManager.PERMISSION_GRANTED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GetData()
        // on below line we are checking permission
        if (checkPermissions()) {
            // if permission is granted we are displaying a toast message.
            Toast.makeText(context, "Permissions Granted..", Toast.LENGTH_SHORT).show()
        } else {
            // if the permission is not granted
            // we are calling request permission method.
            requestPermission()
        }
        binding.saveCV.setOnClickListener {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                // on below line we are calling generate
                // PDF method to generate our PDF file.
                generateCvPdf()
            }

        }
    }

    fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                activity?.let { it ->
                    ActivityCompat.requestPermissions(
                        it,
                        listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE).toTypedArray(),
                        id + 204
                    )
                }
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
                    Log.d("permission Granted", "Permission granted")
                } else {
                    Toast.makeText(context, "Permission Denied..", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun generateCvPdf() {
        var pdfDocument: PdfDocument = PdfDocument()
        var paint: Paint = Paint()
        var title: Paint = Paint()
        var myPageInfo: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

        var myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)
        var canvas: Canvas = myPage.canvas
        canvas.drawText("A portal for IT professionals.", 209F, 100F, title)
        title.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        title.textSize = 15F
        title.textAlign = Paint.Align.CENTER
        canvas.drawText("This is sample document which we have created.", 396F, 560F, title)

        pdfDocument.finishPage(myPage)
        val file: File = File(Environment.getExternalStorageDirectory(), "GFG.pdf")
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(context, "PDF file generated..", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("err", e.toString())
            Toast.makeText(context, "Fail to generate PDF file..", Toast.LENGTH_SHORT)
                .show()
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

                Log.d("edu", educations?.data.toString())

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