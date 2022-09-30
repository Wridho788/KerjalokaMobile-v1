package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Preference

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentSalaryExpectationBinding
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.profilepage


class FragmentSalaryExpectation(val salaryExpectation: Int?) : Fragment() {
    private lateinit var binding : FragmentSalaryExpectationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSalaryExpectationBinding.inflate(layoutInflater)
        val view = binding.root;
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener{
            back()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }

        binding.salaryExpectationTxt.setText(if(salaryExpectation == null) "" else salaryExpectation.toString())

        binding.saveBtn.setOnClickListener {
            val value = if(binding.salaryExpectationTxt.text.isNullOrEmpty() || binding.salaryExpectationTxt.text.toString().toInt() == 0) null
                        else binding.salaryExpectationTxt.text.toString().toInt()
            ManageProfileAPI().EditSalaryExpectation(value, context){
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
    private fun back(){
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction?.replace(id, profilepage(), "Profile Page")
        fragmentTransaction?.commit()
    }

}