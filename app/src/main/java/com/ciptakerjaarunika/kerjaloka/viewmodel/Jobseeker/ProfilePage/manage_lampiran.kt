package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentManageLampiranPageBinding
import com.ciptakerjaarunika.kerjaloka.enum.DocumentType
import com.ciptakerjaarunika.kerjaloka.enum.VerifyStatus
import com.ciptakerjaarunika.kerjaloka.`interface`.BasicImagePicker
import com.ciptakerjaarunika.kerjaloka.`interface`.RxImagePicker
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.viewmodel.Components.Gallery.DefaultGalleryMimes
import com.ciptakerjaarunika.kerjaloka.viewmodel.Components.Gallery.DefaultSystemGalleryConfig
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter.DocumentAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Attachment.FragmentEditLampiran
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Attachment.fragment_editlampiran_upload_vaksin
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class manage_lampiran : Fragment(), iRefreshData {
    private lateinit var binding: FragmentManageLampiranPageBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private var oldestFile: String? = null
    private lateinit var defaultImagePicker: BasicImagePicker
    private var fileVideo: File? = null

    private fun initRxImagePicker() {
        defaultImagePicker = RxImagePicker.create(BasicImagePicker::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun pickGallery() {
        context?.let {
            defaultImagePicker
                .openGallery(
                    it,
                    DefaultSystemGalleryConfig.instance(
                        mimesType = DefaultGalleryMimes.customTypes("video/*") // multiType
                    )
                )
                .subscribe { result -> onPickUriSuccess(result.uri) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onPickUriSuccess(uri: Uri) {
        val pathName = context?.let { getPathFromUri(it, uri) }
        if (pathName != null) {
            val file = File(pathName)
            if (file != null) {
                val requestFile: RequestBody =
                    file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val files = MultipartBody.Part.createFormData(
                    "files",
                    file.name,
                    requestFile
                )
                this.fileVideo = file
                val m = (fileVideo?.length()?.toDouble()!! / 1024.0 / 1024.0)

                if (m != null) {
                    if (m >= 100) {
                        Toast.makeText(
                            context,
                            "batas maksimal video resume 100 Mb",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        ManageProfileAPI().UploadVideoResume(files, context) {
                            binding.spinnerResume.visibility = VISIBLE
                            binding.uploadVideoResumeBtn.visibility = GONE
                            if (it != null) {
                                binding.spinnerResume.visibility = VISIBLE
                                binding.uploadVideoResumeBtn.visibility = GONE
                                if (it.code == 210) {
                                    Log.d("upload video resume", it.data.toString())
                                    binding.spinnerResume.visibility = GONE
                                    binding.uploadVideoResumeBtn.visibility = VISIBLE
                                    binding.videoResumeName.text = it.data?.videoName
                                }
                                if (it.data != null) {
                                    Log.d("upload", it.data.toString())
                                    binding.videoResumeName.text = it.data.videoName
                                    binding.btnRemoveResume.visibility = VISIBLE
                                    binding.btnRemoveResume.setOnClickListener {
                                        ProfileAPI().DeleteJobseekerResume(context) {
                                            Toast.makeText(
                                                context,
                                                "Berhasil menghapus video resume",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            binding.videoResumeName.text = "Upload Video Resume"
                                            binding.btnRemoveResume.visibility = GONE
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    private fun getPathFromUri(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        return try {
            val project = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, project, null, null, null)
            val columnIndex: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(columnIndex)
        } finally {
            cursor?.close()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageLampiranPageBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRxImagePicker()
        getData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getData() {
        ProfileAPI().GetJobseekerDocuments(context) { documents ->
            binding.editLampiranPelamar.visibility = VISIBLE
            binding.editLampiranPelamar.setOnClickListener {
                replaceFragment(FragmentEditLampiran(documents?.data, this))
            }
            binding.spinnerDoc.visibility = GONE
            val recyclerView = view?.findViewById<RecyclerView>(R.id.RecyclerAttachment)
            recyclerView?.visibility = VISIBLE
            recyclerView?.layoutManager = LinearLayoutManager(activity)
            recyclerView?.adapter = documents?.data?.let { DocumentAdapter(it) }
        }

        ProfileAPI().GetJobseekerResume(context) { resume ->
            if (activity != null) {
                binding.spinnerResume.visibility = GONE
                binding.uploadVideoResumeBtn.visibility = VISIBLE
                binding.uploadVideoResumeBtn.setOnClickListener {
                    pickGallery()
                }

                if (resume?.data != null) {
                    val resumeDoc = resume.data
                    oldestFile = resumeDoc.videoName
                    binding.videoResumeName.text = resumeDoc.videoName
                    binding.btnRemoveResume.visibility = VISIBLE
                    binding.btnRemoveResume.setOnClickListener {
                        ProfileAPI().DeleteJobseekerResume(context) {
                            Toast.makeText(
                                context,
                                "Berhasil menghapus video resume",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.videoResumeName.text = "Upload Video Resume"
                            binding.btnRemoveResume.visibility = GONE
                        }
                    }
                }
            }
        }

        ProfileAPI().GetJobseekerDocumentVaccine(context) { vaccine ->
            if (activity != null) {
                binding.spinnerVac.visibility = GONE
                binding.vaccineContainer.visibility = VISIBLE
                binding.editStatusVaksinPelamar.visibility = VISIBLE

                if (vaccine != null) {
                    binding.editStatusVaksinPelamar.setOnClickListener {
                        replaceFragment(
                            fragment_editlampiran_upload_vaksin(
                                vaccine.data
                                    .filter { doc ->
                                        doc.documentType == DocumentType.Vaccine1.value ||
                                                doc.documentType == DocumentType.Vaccine2.value ||
                                                doc.documentType == DocumentType.Vaccine3.value
                                    }, this
                            )
                        )
                    }
                }
                if (vaccine != null && vaccine.data.size != 0) {
                    for (doc in vaccine.data) {
                        var vaccineLogo: ImageView = view!!.findViewById(R.id.vaccine1Status)
                        if (doc.documentType == DocumentType.Vaccine1.value) {
                            vaccineLogo = view!!.findViewById(R.id.vaccine1Status)
                        } else if (doc.documentType == DocumentType.Vaccine2.value) {
                            vaccineLogo = view!!.findViewById(R.id.vaccine2Status)
                        } else if (doc.documentType == DocumentType.Vaccine3.value) {
                            vaccineLogo = view!!.findViewById(R.id.vaccine3Status)
                        }

                        when (doc.documentStatus) {
                            VerifyStatus.Accept.value -> {
                                vaccineLogo.setImageResource(R.drawable.ic_vaccine_approve)
                            }
                            VerifyStatus.Reject.value -> {
                                vaccineLogo.setImageResource(R.drawable.ic_vaccine_reject)
                            }
                            VerifyStatus.Pending.value -> {
                                vaccineLogo.setImageResource(R.drawable.ic_vaccine_pending)
                            }
                        }
                    }
                }
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun refresh() {
        getData()
    }
}