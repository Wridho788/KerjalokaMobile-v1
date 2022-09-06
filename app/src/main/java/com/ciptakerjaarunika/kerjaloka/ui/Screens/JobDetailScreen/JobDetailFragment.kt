package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.jobLocation
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.BottomSheet.ApplyJob
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.BottomSheet.ReportJob
import com.google.android.material.appbar.MaterialToolbar
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class JobDetailFragment(
    private val JobNo: Long, private val CompanyNo: Long,
) : Fragment(),
    OnFragmentClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setContentView(root: ConstraintLayout) {
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    @SuppressWarnings("deprecation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_job_detail, container, false)
        val btn_applyJob = view.findViewById<View>(R.id.apply_job_button)
        val report_job = view.findViewById<View>(R.id.report_job)

        val company_logo = view.findViewById<ImageView>(R.id.logo_company)
        var job_position = view.findViewById<TextView>(R.id.jobTitle)
        val company_name = view.findViewById<TextView>(R.id.jobCompany)
        val job_location = view.findViewById<TextView>(R.id.jobLocation)
        val job_qualications = view.findViewById<TextView>(R.id.jobQualification)
        val job_experience = view.findViewById<TextView>(R.id.jobExperience)
        val job_field = view.findViewById<TextView>(R.id.jobField)
        val job_role = view.findViewById<TextView>(R.id.jobRole)
        val createdOn = view.findViewById<TextView>(R.id.jobDate)
        val job_description = view.findViewById<TextView>(R.id.jobRequirement)
        val job_salary_min = view.findViewById<TextView>(R.id.jobSalaryMin)
        val job_salary_max = view.findViewById<TextView>(R.id.jobSalaryMax)
        val location = view.findViewById<TextView>(R.id.location)
        val toolbarShare = view.findViewById<ImageView>(R.id.toolbar_share)

        val recyclerView =
            view.findViewById<RecyclerView>(R.id.recycler_view_recommendation_jobs)
        val Context = this
        JobAPI().getJobDetailAsync(context, CompanyNo, JobNo) {
            if (it != null) {
                Log.d("response", it.toString())
                Glide.with(this)
                    .load(config().portAddress + "/photo/Profile/" + it.data.company.logo)
                    .fitCenter().into(company_logo)
                job_position.text = it.data.jobPosition
                if (it.data.jobLocation.size > 1) {
                    job_location.text = "Banyak Lokasi"
                } else {
                    job_location.text = it.data.jobLocation[0].label
                }

                company_name.text = it.data.company.companyName
                job_qualications.text =
                    it.data.jobTitle.joinToString { data -> data.titleName + " " }
                if (it.data.jobMinExperience == 0) {
                    job_experience.text = ""
                } else {
                    job_experience.text = it.data.jobMinExperience.toString() + " Tahun"
                }

                val localeID = Locale("in", "ID")
                val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
                if (it.data.jobSalaryMin == null) {
                    job_salary_min.text = "Rp. 0 -"
                }
                if (it.data.jobSalaryMax == null) {
                    job_salary_max.text = "Rp. 0"
                }
                if (it.data.jobSalaryMin != null) {
                    var salary_min = formatRupiah.format(it.data.jobSalaryMin.toBigDecimal())
                    job_salary_min.text = salary_min?.toString() + " - "
                }
                if (it.data.jobSalaryMax != null) {
                    var salary_max = formatRupiah.format(it.data.jobSalaryMax.toBigDecimal())
                    job_salary_max.text = salary_max?.toString()
                }

//                location.text =
//                    it.data.companyjob.city.cityName + "," + it.data.companyjob.province.provinceName

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
                    return dateFormat.parse(date)
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
                createdOn.text = dateDiff()

                job_field.text = if (it.data.jobField != null) it.data.jobField.fieldName else ""
                job_role.text = if (it.data.jobRole != null) it.data.jobRole.jobRoleName else ""

                var jobDesc = it.data.jobDescription
                if (jobDesc == null) {
                    job_description.text = ""
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    job_description.text = Html.fromHtml(jobDesc, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    job_description.text = Html.fromHtml(jobDesc)
                }

                if (it.data.jobLocation != null ) {
                    val someArray : Array<List<jobLocation>> =  arrayOf(it.data.jobLocation);
                    location.text = someArray.toString()
                }
//                location.text = it.data.jobLocation[0].label

//                recyclerView.apply {
//                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                    adapter = RelatedJobAdapter(it.data.job, Context)
//                }

                val titleJob = it.data.jobPosition.toString()
                val link = it.data.link
                toolbarShare.setOnClickListener {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TITLE, titleJob)
                        putExtra(Intent.EXTRA_TEXT, link)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)

                }
            }
        }

        btn_applyJob.setOnClickListener {
            val sheet = ApplyJob()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "ApplyJob") }
        }

        report_job.setOnClickListener {
            val sheet = ReportJob()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "ReportJob") }

        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val toolbarBookmark = view.findViewById<ImageView>(R.id.toolbar_bookmark)
        val recyclerView2 =
            view.findViewById<RecyclerView>(R.id.recycler_view_related_jobs)

//        recyclerView2.apply {
//            layoutManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            recyclerView2.layoutManager = layoutManager2
//            adapter2 = RelatedOtherJobAdapter()
//            recyclerView2.adapter = adapter2
//        }

        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        toolbarBookmark.setOnClickListener {
            Toast.makeText(context, "Bookmark", Toast.LENGTH_SHORT).show()
        }


        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    override fun onFragmentClick(companyNo: Long, jobNo: Long) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, JobDetailFragment(JobNo, CompanyNo), "jobDetailFragment")
        ft.addToBackStack("jobDetailFragment")
        ft.commit()
    }

    companion object
}

interface OnFragmentClickListener {

    fun onFragmentClick(companyNo: Long, jobNo: Long)
}