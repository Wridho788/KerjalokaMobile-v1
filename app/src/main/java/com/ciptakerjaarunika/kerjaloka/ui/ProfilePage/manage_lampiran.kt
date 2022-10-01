package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.enum.DocumentType
import com.ciptakerjaarunika.kerjaloka.enum.VerifyStatus
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.DocumentAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Attachment.FragmentEditLampiran
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Attachment.fragment_editlampiran_upload_vaksin

class manage_lampiran : Fragment() {

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
            if (resume?.data != null) {
                val resumeDoc = resume.data
                view.findViewById<TextView>(R.id.videoResumeName).text = resumeDoc.videoName
                view.findViewById<ImageView>(R.id.btn_remove_resume).visibility = VISIBLE
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