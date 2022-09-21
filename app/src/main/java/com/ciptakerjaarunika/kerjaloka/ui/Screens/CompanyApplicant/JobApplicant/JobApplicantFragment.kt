package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.api.companyApplicant.CompanyListApplicantAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.enum.ApplicanStatusType
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.ApplicantDetailFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.Adapter.ApplicantAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.Model.applicantModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class JobApplicantFragment(private val JobNo: Long) : Fragment(), OnFragmentClickListener {

    private lateinit var binding: ActivityMainBinding
    private var list: List<applicantModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_job_applicant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val logo = view.findViewById<ImageView>(R.id.logo_company_applicant)
        val title_job = view.findViewById<TextView>(R.id.job_title_applicant)
        val location_job = view.findViewById<TextView>(R.id.job_location_applicant)
        val createdOn_text = view.findViewById<TextView>(R.id.txt_uploadAt)
        val status_job = view.findViewById<TextView>(R.id.status_applicant_text)
        val rv_applicant = view.findViewById<RecyclerView>(R.id.rv_list_applicant)
        val btn_arrow = view.findViewById<ImageView>(R.id.ic_arrow)
        val btn_expand = view.findViewById<ConstraintLayout>(R.id.layout_info_lowongan)
        val layout_content_info = view.findViewById<LinearLayout>(R.id.layout_content_info)

        val totalApplicantText = view.findViewById<TextView>(R.id.totalPelamarText)
        val pelamarBaruText = view.findViewById<TextView>(R.id.totalPelamarBaruText)
        val totalPelamarTerpilih = view.findViewById<TextView>(R.id.totalTerpilihText)
        val totalTestingText = view.findViewById<TextView>(R.id.totalTestingText)
        val totalInterviewText = view.findViewById<TextView>(R.id.totalInterviewText)
        val totalDiterimaText = view.findViewById<TextView>(R.id.totalDiterimaText)
        val totalRejectedText = view.findViewById<TextView>(R.id.totalRejectedText)
        val totalCVbanksText = view.findViewById<TextView>(R.id.totalCVbanksText)

        val toolbar = view.findViewById<ImageView>(R.id.btn_back_job)
        toolbar.setOnClickListener {
            activity?.onBackPressed()
        }

        val companyNo = SessionManager(context).user!!.userNo

        JobAPI().getJobDetailAsync(context, companyNo, JobNo) {
            if (it != null) {
                title_job.text = it.data.jobPosition
                Glide.with(this)
                    .load(config().portAddress + "/photo/Profile/" + it.data.company.logo)
                    .fitCenter().into(logo)
                if (it.data.jobLocation.size > 1) {
                    location_job.text = "Banyak Lokasi"
                } else {
                    location_job.text = it.data.jobLocation[0].label
                }
                val SECOND = 1
                val MINUTE = 60 * SECOND
                val HOUR = 60 * MINUTE
                val DAY = 24 * HOUR
                val WEEK = 7 * DAY

                var time = it.data.createdOn
                val now = LocalDateTime.now().toString()

                fun GetDateValue(value: String): Date {
                    val temp = value.split("T")
                    val time = temp[1].split(":")
                    val date = "${temp[0]} ${time[0]}:${time[1]}"
                    var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
                    return dateFormat.parse(date)!!
                }

                fun dateDiff(): String {
                    val date1 = GetDateValue(time).time
                    val date2 = GetDateValue(now).time

                    val diff = (date2 - date1) / 1000
                    return when {
                        diff < MINUTE -> "Baru Saja"
                        diff < 2 * MINUTE -> "Beberapa Menit Lalu"
                        diff < 60 * MINUTE -> "${diff / MINUTE} Menit Lalu"
                        diff < 2 * HOUR -> "Beberapa Jam Lalu"
                        diff < 24 * HOUR -> "${diff / HOUR} Jam Lalu"
                        diff < 2 * DAY -> "Kemarin"
                        diff < WEEK -> "${diff / DAY} Hari Lalu"
                        else -> LocalDateTime.parse(time)
                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    }
                }
                createdOn_text.text = dateDiff()
                val status = it.data.publish
                if (status == true) {
                    status_job.text = "Aktif"
                    status_job.setTextColor(Color.parseColor("#27AE60"))
                } else {
                    status_job.text = "Tidak Aktif"
                    status_job.setTextColor(Color.parseColor("#C12929"))
                }
            }
        }

        CompanyListApplicantAPI().GetListApplicantPost(context, JobNo) {
            if (it != null) {
                list = it.data
                rv_applicant.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = ApplicantAdapter(list!!, this@JobApplicantFragment)
                }
                val totalApplicant = list!!.size
                totalApplicantText.text = totalApplicant.toString() + " Orang"

                val newApplicant =
                    list!!.filter { it.application.applicationStatusNo == ApplicanStatusType.Applied.value }.size
                pelamarBaruText.text = newApplicant.toString() + " Orang"

                val shortList = list!!.filter { it.application.applicationStatusNo == ApplicanStatusType.ShortList.value}.size
                totalPelamarTerpilih.text = shortList.toString() + " Orang"

                val testList = list!!.filter { it.application.applicationStatusNo == ApplicanStatusType.Test.value}.size
                totalTestingText.text = testList.toString() + " Orang"

                val interviewList = list!!.filter { it.application.applicationStatusNo == ApplicanStatusType.Interview.value}.size
                totalInterviewText.text = interviewList.toString() + " Orang"

                val totalAcceptedList = list!!.filter { it.application.applicationStatusNo == ApplicanStatusType.Accepted.value}.size
                totalDiterimaText.text = totalAcceptedList.toString() + " Orang"

                val totalRejectedList = list!!.filter { it.application.applicationStatusNo == ApplicanStatusType.Rejected.value}.size
                totalRejectedText.text = totalRejectedList.toString() + " Orang"

                val cvBanksList = list!!.filter { it.application.applicationStatusNo == ApplicanStatusType.CVBank.value}.size
                totalCVbanksText.text = cvBanksList.toString() + " Orang"
            }
        }
        btn_expand.setOnClickListener {
            if (layout_content_info.isVisible == isVisible) {
                layout_content_info.visibility = View.GONE
                btn_arrow.setImageResource(R.drawable.ic_arrow_down)
            } else {
                layout_content_info.visibility = View.VISIBLE
                btn_arrow.setImageResource(R.drawable.ic_arrow_up)
            }

        }
    }

    override fun goToApplicantDetail() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, ApplicantDetailFragment(), "company applicant detail")
        ft.addToBackStack("CompanyApplicantDetail")
        ft.commit()
    }
}

interface OnFragmentClickListener {
    fun goToApplicantDetail()
}