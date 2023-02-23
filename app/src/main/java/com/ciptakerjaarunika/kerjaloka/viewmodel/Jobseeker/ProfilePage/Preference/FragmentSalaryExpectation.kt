package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Preference

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentSalaryExpectationBinding
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData


class FragmentSalaryExpectation(val salaryExpectation: Int?, val iRefreshData: iRefreshData) :
    Fragment() {
    private lateinit var binding: FragmentSalaryExpectationBinding
    var salary: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSalaryExpectationBinding.inflate(layoutInflater)
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

        binding.salaryExpectationTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.salaryExpectationTxt.text.toString().toLong() > 10000000000) {
                    Toast.makeText(
                        context,
                        "Ekspektasi Gaji Tidak Boleh Melebihi 10 Miliar",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.salaryExpectationTxt.text.toString().toLong() < 10000000000) {
                    salary = binding.salaryExpectationTxt.text.toString()
                } else if (binding.salaryExpectationTxt.text.toString().isNullOrEmpty()) {
                    Toast.makeText(context, "Silahkan Isi Ekspektasi Gaji", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        binding.saveBtn.setOnClickListener {
            if (salary.length === 0) {
                ManageProfileAPI().EditSalaryExpectation(0, context) {
                    if (it != null) {
                        Toast.makeText(activity, "Berhasil mengubah data", Toast.LENGTH_SHORT)
                            .show()
                        back()
                    } else {
                        Toast.makeText(
                            activity,
                            "Terjadi kesalahan yang tidak diketahui",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                ManageProfileAPI().EditSalaryExpectation(salary.toInt(), context) {
                    if (it != null) {
                        Toast.makeText(activity, "Berhasil mengubah data", Toast.LENGTH_SHORT)
                            .show()
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
    }

    private fun back() {
        fragmentManager?.popBackStack()
        iRefreshData.refresh()
    }

}