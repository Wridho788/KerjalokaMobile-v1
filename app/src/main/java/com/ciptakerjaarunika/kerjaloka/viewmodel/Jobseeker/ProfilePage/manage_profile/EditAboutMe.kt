package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.manage_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentEditAboutMeBinding
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerProfile
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.profilepage

class EditAboutMe(var data : JobseekerProfile?) : Fragment() {
    private lateinit var binding : FragmentEditAboutMeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditAboutMeBinding.inflate(layoutInflater)
        val view = binding.root
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener{
            back()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }

        if(!data?.additionals?.jobseekerAbout.isNullOrEmpty()){
            binding.aboutTxt.setText(data?.additionals?.jobseekerAbout)
        }
        binding.saveBtn.setOnClickListener{
            if(binding.aboutTxt.text.toString().isNullOrEmpty()){
                Toast.makeText(activity,  "Beri tahu tentang dirimu supaya kamu lebih dikenal oleh perusahaan.", Toast.LENGTH_SHORT).show()
            }
            else{
                ManageProfileAPI().EditAboutMe(binding.aboutTxt.text.toString(), context){
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
    }
    private fun back(){
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction?.replace(id, profilepage(0), "Profile Page")
        fragmentTransaction?.commit()
    }

}