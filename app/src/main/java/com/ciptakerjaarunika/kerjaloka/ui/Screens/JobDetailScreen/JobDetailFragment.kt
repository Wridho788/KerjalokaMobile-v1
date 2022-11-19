package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.CompanyDetailAPI
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentJobDetailBinding
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rJobDetailModel
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetail.Adapter.RelatedOtherCompanyJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.Adapter.RelatedJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.BottomSheet.ApplyJob
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.BottomSheet.ReportJob
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class JobDetailFragment(
    private val JobNo: Long, private val CompanyNo: Long?
) : Fragment(), IJobDetail {
    private lateinit var binding: FragmentJobDetailBinding
    private var jobBookmark = false
    private var currentJob: rJobDetailModel? = null

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    @SuppressWarnings("deprecation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJobDetailBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val toolbarBookmark = view.findViewById<ImageView>(R.id.toolbar_bookmark)
        RefreshData()
        binding.backButton.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        toolbar.setNavigationOnClickListener {
            fragmentManager?.popBackStack()
        }
        toolbarBookmark.setOnClickListener {
            Toast.makeText(context, "Bookmark", Toast.LENGTH_SHORT).show()
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    override fun RefreshData() {
        val view = view
        if (view != null) {
            val btn_applyJob = view.findViewById<MaterialButton>(R.id.apply_job_button)
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
            val toolbarBookmark = view.findViewById<ImageView>(R.id.toolbar_bookmark)

            view.findViewById<LinearLayout>(R.id.spinnerDetailPekerjaan).visibility = VISIBLE
            view.findViewById<NestedScrollView>(R.id.job_detail_container).visibility = GONE
            JobAPI().getJobDetailAsync(context, CompanyNo, JobNo) {
                if (it != null) {
                    currentJob = it.data

                    view.findViewById<LinearLayout>(R.id.spinnerDetailPekerjaan).visibility = GONE
                    view.findViewById<NestedScrollView>(R.id.job_detail_container).visibility =
                        VISIBLE
                    jobBookmark = it.data.bookmarked == true


                    if (activity != null && !activity!!.isDestroyed) {
                        Glide.with(this)
                            .load(config().portAddress + "photo/Profile/" + it.data.company.logo)
                            .fitCenter().into(company_logo)
                    }

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
                    if (it.data.jobSalaryMin != null && it.data.jobSalaryMin != null) {
                        if (it.data.jobSalaryMin == null) {
                            job_salary_min.text = "Rp. 0 -"
                        }
                        if (it.data.jobSalaryMax == null) {
                            job_salary_max.text = "Rp. 0"
                        }
                        if (it.data.jobSalaryMin != null) {
                            var salary_min =
                                formatRupiah.format(it.data.jobSalaryMin.toBigDecimal())
                            job_salary_min.text = salary_min?.toString() + " - "
                        }
                        if (it.data.jobSalaryMax != null) {
                            var salary_max =
                                formatRupiah.format(it.data.jobSalaryMax.toBigDecimal())
                            job_salary_max.text = salary_max?.toString()
                        }
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

                    job_field.text =
                        if (it.data.jobField != null) it.data.jobField.fieldName else ""
                    job_role.text = if (it.data.jobRole != null) it.data.jobRole.jobRoleName else ""

                    var jobDesc = it.data.jobDescription
                    if (jobDesc == null) {
                        job_description.text = ""
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        job_description.text = Html.fromHtml(jobDesc, Html.FROM_HTML_MODE_LEGACY)
                    } else {
                        job_description.text = Html.fromHtml(jobDesc)
                    }
                    if (it.data.jobLocation != null) {
                        var locationText = ""
                        for (location in it.data.jobLocation) {
                            locationText += "&#8226; ${location.label}<br/>"
                        }
                        location.text = Html.fromHtml(locationText)
                    }

                    //                recyclerView.apply {
                    //                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    //                    adapter = RelatedJobAdapter(it.data.job, Context)
                    //                }

                    val titleJob = it.data.jobPosition.toString()
                    val link = it.data.link
                    val job = it.data
                    Log.d("link", link.toString())
                    toolbarShare.setOnClickListener {
                        val text =
                            "${job.company.companyName}\n" +
                                    "sedang membuka lowongan pekerjaan sebagai '${job.jobPosition}'.\n" +
                                    "Lihat informasi selengkapnya ${link}"
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TITLE, job.jobPosition)
                            putExtra(Intent.EXTRA_TEXT, text)
                            type = "text/plain"
                        }

                        val shareIntent =
                            Intent.createChooser(sendIntent, "Bagikan Informasi Pekerjaan")
                        startActivity(shareIntent)
                    }

                    if (SessionManager(context).user == null) {
                        toolbarBookmark.visibility = GONE
                        btn_applyJob.setBackgroundResource(R.drawable.button_primary_disabled)
                        btn_applyJob.setOnClickListener {
                            Toast.makeText(
                                context,
                                "Silahkan login terlebih dahulu untuk dapat melamar pekerjaan",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        toolbarBookmark.setImageResource(if (jobBookmark) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark)

                        toolbarBookmark.setOnClickListener {
                            JobAPI().BookmarkJob(job.jobNo, !jobBookmark, context) {
                                Log.d("Bookmark Response", it.toString())
                                if (it != null) {
                                    if (it.code == 210) {
                                        jobBookmark = !jobBookmark
                                        toolbarBookmark.setImageResource(if (jobBookmark == true) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark)
                                    } else {
                                        Toast.makeText(context, it.Message, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            }
                        }
                        if (currentJob!!.applied == true) {
                            btn_applyJob.text = "Sudah Melamar"
                            btn_applyJob.setBackgroundResource(R.drawable.button_primary_disabled)
                            btn_applyJob.setOnClickListener {}
                        } else {
                            btn_applyJob.setOnClickListener {
                                if (currentJob?.jobShortQuestion!!.any()) {
                                    JobAPI().GetJobShortQuestion(job.jobNo, context) {
                                        if (it != null) {
                                            val sheet = ApplyJob(currentJob, it.data, this)
                                            activity?.let { it1 ->
                                                sheet.show(
                                                    it1.supportFragmentManager,
                                                    "ApplyJob"
                                                )
                                            }
                                        }
                                    }
                                } else {
                                    val sheet = ApplyJob(currentJob, listOf(), this)
                                    activity?.let { it1 ->
                                        sheet.show(
                                            it1.supportFragmentManager,
                                            "ApplyJob"
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(context, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show()
                    view.findViewById<LinearLayout>(R.id.spinnerDetailPekerjaan).visibility = GONE
                    fragmentManager?.popBackStack()
                }
//                else {
//                    val intent = Intent(context, PageNotFoundActivity()::class.java)
//                    startActivity(intent)
//                }
            }

            report_job.setOnClickListener {
                val sheet = ReportJob(JobNo)
                activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "ReportJob") }

            }
            if (CompanyNo == null || CompanyNo == 0L) {
                view.findViewById<LinearLayout>(R.id.other_job_container).visibility = GONE
            } else {
                CompanyDetailAPI().getCompanyDetailAsync(context, CompanyNo) {
                    if (it != null) {
                        view.findViewById<LinearLayout>(R.id.spinnerOtherJobCompany).visibility =
                            GONE
                        val recyclerView =
                            view.findViewById<RecyclerView>(R.id.recycler_other_job_company)
                        recyclerView.visibility = VISIBLE
                        val otherJob = it.data.job.filter { job -> job.jobNo != currentJob?.jobNo }
                        if (otherJob.isNotEmpty()) {
                            recyclerView.apply {
                                layoutManager = LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                                adapter =
                                    RelatedOtherCompanyJobAdapter(otherJob, this@JobDetailFragment)
                            }
                        } else {
                            view.findViewById<LinearLayout>(R.id.other_job_container).visibility =
                                GONE
                        }
                    }
                }
            }
            JobAPI().getRelatedJob(JobNo, context) {
                if (it != null) {
                    if (it.data.size == 0) view.findViewById<LinearLayout>(R.id.related_job_container).visibility =
                        GONE

                    view.findViewById<LinearLayout>(R.id.spinnerRelatedJob).visibility = GONE
                    val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_related_job)
                    recyclerView.visibility = VISIBLE

                    recyclerView.apply {
                        layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        recyclerView.layoutManager = layoutManager
                        adapter = RelatedJobAdapter(it.data, this@JobDetailFragment)
                    }
                } else {
                    view.findViewById<LinearLayout>(R.id.related_job_container).visibility = GONE
                }
            }
        }
    }

    override fun onFragmentClick(companyNo: Long, jobNo: Long) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, JobDetailFragment(jobNo, companyNo), "jobDetailFragment")
        ft.addToBackStack("jobDetailFragment")
        ft.commit()
    }

    companion object
}

interface IJobDetail {
    fun RefreshData()
    fun onFragmentClick(companyNo: Long, jobNo: Long)
}