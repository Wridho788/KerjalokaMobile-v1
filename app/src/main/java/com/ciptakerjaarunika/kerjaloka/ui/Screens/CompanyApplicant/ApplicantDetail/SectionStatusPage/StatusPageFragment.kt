package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionStatusPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentStatusPageBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionStatusPage.BottomSheet.UbahStatusFragment
import com.google.android.material.card.MaterialCardView
import java.util.*

class StatusPageFragment : Fragment() {
    private lateinit var binding: FragmentStatusPageBinding
    lateinit var datePicker: DatePickerHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatusPageBinding.inflate(layoutInflater)
        val view = binding.root
        return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datePicker = DatePickerHelper(context!!)
        val btn_status = view.findViewById<MaterialCardView>(R.id.btn_status)
        val btn_change_status = view.findViewById<MaterialCardView>(R.id.btn_change_status)
        binding.btnBackStatus.setOnClickListener {
            activity?.onBackPressed()
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        btn_status.setOnClickListener {
            val sheet = UbahStatusFragment()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "StatusFragment")}
        }

        btn_change_status.setOnClickListener {
            Toast.makeText( activity,"change status", Toast.LENGTH_SHORT).show()
        }

        binding.btnDatePicker.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)
        datePicker.showDialog(d, m, y, object : DatePickerHelper.Callback {
            override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {
                val dayStr = if (dayofMonth < 10) "0${dayofMonth}" else "${dayofMonth}"
                val mon = month + 1
                val monthStr = if (mon < 10) "0${mon}" else "${mon}"
                binding.textDate.text = "${dayStr}-${monthStr}-${year}"
            }
        })
    }

}