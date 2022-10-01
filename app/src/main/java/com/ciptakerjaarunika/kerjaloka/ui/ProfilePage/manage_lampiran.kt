package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.enum.DocumentType
import com.ciptakerjaarunika.kerjaloka.enum.VerifyStatus
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.DocumentAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Attachment.FragmentEditLampiran
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Attachment.fragment_editlampiran_upload_vaksin
import com.qingmei2.rximagepicker_extension.utils.PathUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class manage_lampiran : Fragment() {
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    private var oldestFile : String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_manage_lampiran_page_profile, container, false)
        val btn_EdLamp = view.findViewById<TextView>(R.id.edit_lampiran_pelamar)
        val spinnerDoc = view.findViewById<LinearLayout>(R.id.spinnerDoc)
//        val btn_edResume = view.findViewById<TextView>(R.id.edit_video_resume_pelamar)
        val btn_edVaccine = view.findViewById<TextView>(R.id.edit_status_vaksin_pelamar)
        val btnResume = view.findViewById<TextView>(R.id.videoResumeName)

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
                val photo = MultipartBody.Part.createFormData("photo", file.name, requestFile)

                ManageProfileAPI().JobseekerUploadResume(photo, oldestFile, context){res->
                    if(res?.data != null){
                        btnResume.text = res.data.videoName
                    }
                }

            }
        }

        ProfileAPI().GetJobseekerDocuments(context){ documents ->
            btn_EdLamp.setOnClickListener{
                replaceFragment(FragmentEditLampiran(documents?.data))
            }

            spinnerDoc.visibility = GONE
            val recyclerView = view?.findViewById<RecyclerView>(R.id.RecyclerAttachment)
            recyclerView?.visibility = VISIBLE
            recyclerView?.layoutManager = LinearLayoutManager(activity)
            recyclerView?.adapter = documents?.data?.let { DocumentAdapter(it) }
        }



        ProfileAPI().GetJobseekerResume(context){ resume ->
<<<<<<< HEAD
            btnResume.setOnClickListener {
                var intent = Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                val requestIntent = Intent.createChooser(intent, "Choose a Video");
                activityResultLauncher.launch(requestIntent)
            }
=======
>>>>>>> 9908e33047bf278ccebcf8485d23451f458cc60b
            if (resume?.data != null) {
                val resumeDoc = resume.data
                oldestFile = resumeDoc.videoName;
                btnResume.text = resumeDoc.videoName
                val btnRemove = view.findViewById<ImageView>(R.id.btn_remove_resume)
                btnRemove.visibility = VISIBLE
                btnRemove.setOnClickListener {
                    ProfileAPI().DeleteJobseekerResume(context){
                        Toast.makeText(context, "Berhasil menghapus video resume", Toast.LENGTH_SHORT).show()
                        btnResume.text = "Upload Video Resume"
                    }
                }
            }
        }

        ProfileAPI().GetJobseekerDocumentVaccine(context){vaccine->
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
        btn_edVaccine.setOnClickListener{
            replaceFragment(fragment_editlampiran_upload_vaksin())
        }
        return view
    }


    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }
}