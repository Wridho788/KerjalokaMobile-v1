package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.manage_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentEditAddInfoBinding
import com.ciptakerjaarunika.kerjaloka.model.Data.Marital
import com.ciptakerjaarunika.kerjaloka.model.Data.Religion
import com.ciptakerjaarunika.kerjaloka.model.Data.Resident
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerProfile
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit.EditMarital
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit.EditReligion
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit.EditResident
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.profilepage

class EditAddInfo(val data: JobseekerProfile?) : Fragment(), iUpdateAdditional {
    private lateinit var binding: FragmentEditAddInfoBinding
    private var maritalNo: Int? = data?.additionals?.maritalNo
    private var residentNo: Int? = data?.additionals?.residentNo
    private var religionNo: Int? = data?.additionals?.religionNo
    private var residents: List<Resident> = listOf()
    private var religions: List<Religion> = listOf()
    private var maritals: List<Marital> = listOf()
    private var loading = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataAPI().GetMaritals(context) {
            if (it != null) {
                maritals = it
            }
            loading -= 1
            checkLoading()
        }
        DataAPI().GetReligions(context) {

            if (it != null) {
                religions = it
            }
            loading -= 1
            checkLoading()
        }
        DataAPI().GetResidents(context) {

            if (it != null) {
                residents = it
            }
            loading -= 1
            checkLoading()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditAddInfoBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {
            back()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }

        binding.jsEditEthnic.setText(data?.additionals?.ethnics)
        binding.birhtdayPlace.setText(data?.additionals?.placeOfBirth)
        binding.postalCode.setText(data?.additionals?.postalCode)
        binding.telegram.setText(data?.additionals?.telegramId)
        binding.instagram.setText(data?.additionals?.instagramId)

        binding.saveBtn.setOnClickListener {
            ManageProfileAPI().EditAdditional(
                ManageProfileAPI.editAdditionalRequest(
                    maritalNo,
                    religionNo,
                    binding.postalCode.text.toString(),
                    binding.birhtdayPlace.text.toString(),
                    binding.jsEditEthnic.text.toString(),
                    residentNo,
                    binding.telegram.text.toString(),
                    binding.instagram.text.toString()
                ), context
            ) {
                if (it != null) {
                    Toast.makeText(activity, "Berhasil mengubah data", Toast.LENGTH_SHORT).show()
                    back()
                } else {
                    Toast.makeText(
                        activity,
                        "Terjadi kesalahan yang tidak diketahui",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun updateAdditional(value: Int, type: String) {
        when (type) {
            "marital" -> {
                maritalNo = value
                var currentMarital = maritals.find { item -> item.maritalNo == maritalNo }
                if (currentMarital != null) {
                    binding.jsEditMarital.text = currentMarital.maritalName
                }
            }
            "religion" -> {
                religionNo = value
                var currentReligion = religions.find { item -> item.religionNo == religionNo }
                if (currentReligion != null) {
                    binding.jsEditReligi.text = currentReligion.religionName
                }
            }
            "resident" -> {
                residentNo = value
                var currentResident = residents.find { item -> item.residentNo == residentNo }
                if (currentResident != null) {
                    binding.jsEditResident.text = currentResident.residentName
                }
            }
        }
    }

    private fun back() {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(id, profilepage(0), "Profile Page")
        fragmentTransaction.commit()
    }

    private fun checkLoading() {
        if (loading <= 0) {
            binding.jsEditMarital.setOnClickListener {
                activity?.let { it1 ->
                    EditMarital(maritalNo, maritals, this).show(
                        it1.supportFragmentManager,
                        "DemoBottomSheetFragment"
                    )
                }
            }
            binding.jsEditReligi.setOnClickListener {
                activity?.let { it1 ->
                    EditReligion(religionNo, religions, this).show(
                        it1.supportFragmentManager,
                        "DemoBottomSheetFragment"
                    )
                }
            }
            binding.jsEditResident.setOnClickListener {
                activity?.let { it1 ->
                    EditResident(residentNo, residents, this).show(
                        it1.supportFragmentManager,
                        "DemoBottomSheetFragment"
                    )
                }
            }

            var currentReligion =
                religions.find { item -> item.religionNo == data?.additionals?.religionNo }
            var currentResident =
                residents.find { item -> item.residentNo == data?.additionals?.residentNo }
            var currentMarital =
                maritals.find { item -> item.maritalNo == data?.additionals?.maritalNo }

            if (currentReligion != null) {
                binding.jsEditReligi.text = currentReligion.religionName
            }
            if (currentResident != null) {
                binding.jsEditResident.text = currentResident.residentName
            }
            if (currentMarital != null) {
                binding.jsEditMarital.text = currentMarital.maritalName
            }
        }
    }
}

interface iUpdateAdditional {
    fun updateAdditional(value: Int, type: String)
}