package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionStatusPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.api.companyApplicant.*
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentStatusPageBinding
import com.ciptakerjaarunika.kerjaloka.enum.ApplicanStatusType
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionStatusPage.BottomSheet.UbahStatusFragment
import java.util.*

class StatusPageFragment(private val applicantNo: Long, private var applicationStatusNo: Int) :
    Fragment(), iStatusPage {
    private lateinit var binding: FragmentStatusPageBinding
    lateinit var datePicker: DatePickerHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatusPageBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datePicker = DatePickerHelper(context!!)
        binding.btnBackStatus.setOnClickListener {
            activity?.onBackPressed()
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.btnStatus.setOnClickListener {
            ubahStatusModal()
        }
        if (applicationStatusNo == ApplicanStatusType.ShortList.value){
            binding.statusChange.text = "Terpilih"
        } else if (applicationStatusNo == ApplicanStatusType.Test.value){
            binding.statusChange.text = "Dalam Test"
        } else if (applicationStatusNo == ApplicanStatusType.Interview.value) {
            binding.statusChange.text = "Interview"
        } else if (applicationStatusNo == ApplicanStatusType.Accepted.value) {
            binding.statusChange.text = "Diterima"
        } else if (applicationStatusNo == ApplicanStatusType.Rejected.value) {
            binding.statusChange.text = "Ditolak"
        } else {
            binding.statusChange.text = "CV Bank"
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

    fun ubahStatusModal(){
        val sheet = UbahStatusFragment(this@StatusPageFragment)
        activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "StatusFragment")}
    }

    override fun changeStatus(status: Int) {
        if (status == ApplicanStatusType.ShortList.value) {
            binding.statusChange.text = "Terpilih"
            binding.sectionInterview.visibility = View.GONE
            binding.btnChangeStatus.setOnClickListener {
                ShortlistStatus(context,applicantNo){
                    if(it != null){
                        if (it.code == 210) {
                             Log.d("response", it.toString())
                            activity?.onBackPressed()
                        }
                    }
                }
            }
        } else if (status == ApplicanStatusType.Test.value) {
            binding.statusChange.text = "Dalam Test"
            binding.sectionInterview.visibility = View.GONE
            binding.btnChangeStatus.setOnClickListener {
                TestStatus(context, applicantNo){
                    if(it != null){
                        if (it.code == 210) {
                            Log.d("response", it.toString())
                            activity?.onBackPressed()
                        }
                    }
                }
            }
        } else if (status == ApplicanStatusType.Interview.value) {
            binding.statusChange.text = "Interview"
            binding.sectionInterview.visibility = View.VISIBLE
            val locationInterview = binding.txtInputLocation.text.toString()
            val nameInterview = binding.txtInputInterviewer.text.toString()
            binding.btnChangeStatus.setOnClickListener {
              InterviewStatus(context, applicantNo){
                  if (it != null){
                      if (it.code == 210){
                          activity?.onBackPressed()
                      }
                  }
              }
            }
        } else if (status == ApplicanStatusType.Accepted.value) {
            binding.statusChange.text = "Diterima"
            binding.sectionInterview.visibility = View.GONE
            binding.btnChangeStatus.setOnClickListener {
                AcceptedStatus(context, applicantNo){
                    if(it != null){
                        if (it.code == 210) {
                            Log.d("response", it.toString())
                            activity?.onBackPressed()
                        }
                    }
                }
            }
        } else if (status == ApplicanStatusType.Rejected.value) {
            binding.statusChange.text = "Ditolak"
            binding.sectionInterview.visibility = View.GONE
            binding.btnChangeStatus.setOnClickListener {
                RejectedStatus(context, applicantNo){
                    if(it != null){
                        if (it.code == 210) {
                            Log.d("response", it.toString())
                            activity?.onBackPressed()
                        }
                    }
                }
            }
        } else if (status == ApplicanStatusType.CVBank.value) {
            binding.statusChange.text = "CV Bank"
            binding.sectionInterview.visibility = View.GONE
            binding.btnChangeStatus.setOnClickListener {
                CvbankStatus(context, applicantNo){
                    if(it != null){
                        if (it.code == 210) {
                            Log.d("response", it.toString())
                            activity?.onBackPressed()
                        }
                    }
                }
            }
        } else {
            status
        }
        binding.btnDatePicker.setOnClickListener {
            showDatePickerDialog()
        }
    }


}
interface iStatusPage{
   fun changeStatus(status: Int)
}