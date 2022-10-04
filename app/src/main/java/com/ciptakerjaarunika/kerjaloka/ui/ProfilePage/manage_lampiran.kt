package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentManageLampiranPageBinding
import com.ciptakerjaarunika.kerjaloka.enum.DocumentType
import com.ciptakerjaarunika.kerjaloka.enum.VerifyStatus
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.DocumentAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Attachment.FragmentEditLampiran
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Attachment.fragment_editlampiran_upload_vaksin
import com.ciptakerjaarunika.kerjaloka.utils.PathUtil
import com.qingmei2.rximagepicker_extension.utils.PathUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


class manage_lampiran : Fragment() {
    private lateinit var binding : FragmentManageLampiranPageBinding
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    private var oldestFile : String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageLampiranPageBinding.inflate(layoutInflater)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                val data = it.data
                val fileUri: Uri = data!!.data!!
                val path = PathUtil().getRealPath(context!!, fileUri)
                val file: File? = File(path?:"")
                if(file != null){
                    val requestFile: RequestBody =
                        file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val files = MultipartBody.Part.createFormData("files", file.name, requestFile)

                    ManageProfileAPI().UploadVideoResume(files, context){res->
                        if(res?.data != null){
                            binding.videoResumeName.text = res.data.videoName
                        }
                    }
                }
            }
        }

        ProfileAPI().GetJobseekerDocuments(context){ documents ->
            binding.editLampiranPelamar.visibility = VISIBLE
            binding.editLampiranPelamar.setOnClickListener{
                replaceFragment(FragmentEditLampiran(documents?.data))
            }

            binding.spinnerDoc.visibility = GONE
            val recyclerView = view?.findViewById<RecyclerView>(R.id.RecyclerAttachment)
            recyclerView?.visibility = VISIBLE
            recyclerView?.layoutManager = LinearLayoutManager(activity)
            recyclerView?.adapter = documents?.data?.let { DocumentAdapter(it) }
        }



        ProfileAPI().GetJobseekerResume(context){ resume ->
            binding.spinnerResume.visibility = GONE
            binding.uploadVideoResumeBtn.visibility = VISIBLE

            binding.uploadVideoResumeBtn.setOnClickListener {
                var intent = Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                val requestIntent = Intent.createChooser(intent, "Choose a Video");
                activityResultLauncher.launch(requestIntent)
            }

            if (resume?.data != null) {
                val resumeDoc = resume.data
                oldestFile = resumeDoc.videoName;
                binding.videoResumeName.text = resumeDoc.videoName


                binding.btnRemoveResume.visibility = VISIBLE
                binding.btnRemoveResume.setOnClickListener {
                    ProfileAPI().DeleteJobseekerResume(context){
                        Toast.makeText(context, "Berhasil menghapus video resume", Toast.LENGTH_SHORT).show()
                        binding.videoResumeName.text = "Upload Video Resume"
                    }
                }
            }
        }

        ProfileAPI().GetJobseekerDocumentVaccine(context){vaccine->
            binding.spinnerVac.visibility = GONE
            binding.vaccineContainer.visibility = VISIBLE
            binding.editStatusVaksinPelamar.visibility = VISIBLE

            if(vaccine != null) {
                binding.editStatusVaksinPelamar.setOnClickListener {
                    replaceFragment(
                        fragment_editlampiran_upload_vaksin(vaccine.data
                            .filter { doc ->
                                doc.documentType == DocumentType.Vaccine1.value ||
                                        doc.documentType == DocumentType.Vaccine2.value ||
                                        doc.documentType == DocumentType.Vaccine3.value
                            })
                    )
                }
            }
            if(vaccine != null && vaccine.data.size != 0) {
                for (doc in vaccine.data) {
                    var vaccineLogo: ImageView = view.findViewById(R.id.vaccine1Status);
                    if (doc.documentType == DocumentType.Vaccine1.value) {
                        vaccineLogo = view.findViewById(R.id.vaccine1Status)
                    } else if (doc.documentType == DocumentType.Vaccine2.value) {
                        vaccineLogo = view.findViewById(R.id.vaccine2Status)
                    } else if (doc.documentType == DocumentType.Vaccine3.value) {
                        vaccineLogo = view.findViewById(R.id.vaccine3Status)
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

//        btn_edResume.setOnClickListener{
//
//        }

    }


    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }
}