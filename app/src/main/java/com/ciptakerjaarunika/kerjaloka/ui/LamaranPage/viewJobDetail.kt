package com.ciptakerjaarunika.kerjaloka.ui.LamaranPage

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
import android.widget.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.Job.ApplicationData
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditGender
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils
import com.google.android.material.button.MaterialButton
import java.text.NumberFormat
import java.util.*

class viewJobDetail(val JobNo: Long,val CompanyNo: Long,val applicationData: ApplicationData?) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private var jobBookmark = false;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_job_detail, container, false)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        JobAPI().GetJobDetailLogin(context, CompanyNo, JobNo) {
            if (it != null) {
                val job = it.data
                jobBookmark = job.bookmarked == true;
                view.findViewById<LinearLayout>(R.id.spinnerDetailPekerjaan)?.visibility = GONE
                view.findViewById<ScrollView>(R.id.contentPekerjaan)?.visibility = VISIBLE

                var JobPosition = view.findViewById<TextView>(R.id.card_title)
                var CompanyName = view.findViewById<TextView>(R.id.card_companyname)
                var Location = view.findViewById<TextView>(R.id.card_location)
                var Logo = view.findViewById<ImageView>(R.id.img_company_logo)
                var ApplyDate = view.findViewById<TextView>(R.id.date_apply)
                var ApplyTime = view.findViewById<TextView>(R.id.time_apply)

                JobPosition.text = job.jobPosition
                CompanyName.text = job.company.companyName
                Location.text = if(job.jobLocation.size > 1) "Banyak Lokasi" else job.jobLocation[0].label

                context?.let { it1 ->
                    Glide.with(it1)
                        .load(config().portAddress + "/photo/Profile/" + job.company.logo)
                        .into(Logo)
                }
                ApplyDate.text = DateUtils().GetDateValueWithFormat(applicationData?.application?.applyOn, "dd MMM yyyy")
                ApplyTime.text = DateUtils().GetDateValueWithFormat(applicationData?.application?.applyOn, "HH:mm")
                view.findViewById<TextView>(R.id.card_testHasTake)?.text = applicationData?.tests?.filter { it.testResult != null }?.size.toString()
                view.findViewById<TextView>(R.id.card_totalTest)?.text = applicationData?.tests?.size.toString()

                val totalTest = applicationData?.tests?.size
                val hasTake = applicationData?.tests?.filter { it.testResult != null }?.size
                val progressTestValue = if(hasTake == null || hasTake == 0) 0 else (hasTake!! / totalTest!!)* 100
                view.findViewById<ProgressBar>(R.id.progressBar_view)?.progress = progressTestValue
                view.findViewById<TextView>(R.id.progressBar_value)?.text = progressTestValue.toString()
                if(applicationData?.tests?.size == 0 ){
                    view.findViewById<LinearLayout>(R.id.card_progress_container)?.visibility = GONE
                }

                view.findViewById<TextView>(R.id.jobDescription)?.text = if(job.jobDescription != null) Html.fromHtml(job.jobDescription) else "-"

                val localeID = Locale("in", "ID")
                val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
                val salaryMin = job.jobSalaryMin?.toBigDecimal()
                val salaryMax = job.jobSalaryMax?.toBigDecimal()

                if(job.jobSalaryMin == null || job.jobSalaryMax == null){
                    view.findViewById<TextView>(R.id.jobSalaryMin)?.text = " - "
                }
                if(job.jobSalaryMax != null){
                    view.findViewById<TextView>(R.id.jobSalaryMax)?.text = formatRupiah.format(salaryMax)
                }
                if(job.jobSalaryMin != null){
                    view.findViewById<TextView>(R.id.jobSalaryMin)?.text = formatRupiah.format(salaryMin) + " - "
                }
                else if(job.jobSalaryMin != null && job.jobSalaryMax == null){
                    view.findViewById<TextView>(R.id.jobSalaryMin)?.text = formatRupiah.format(salaryMin)
                }

                view.findViewById<TextView>(R.id.jobQualification)?.text = if(job.jobTitle.isEmpty()) "-" else job.jobTitle.joinToString { data -> data.titleName + ", " }
                view.findViewById<TextView>(R.id.jobExperience)?.text = if (job.jobMinExperience != 0) job.jobMinExperience.toString() + " Tahun" else "-"

                view.findViewById<TextView>(R.id.jobField)?.text = if (it.data.jobField != null) it.data.jobField.fieldName else "-"
                view.findViewById<TextView>(R.id.jobRole)?.text = if (it.data.jobRole != null) it.data.jobRole.jobRoleName else "-"

                if (it.data.jobLocation != null ) {
                    var locationText = ""
                    for (location in job.jobLocation){
                        locationText += "&#8226; ${location.label}<br/>"
                    }
                    view.findViewById<TextView>(R.id.locations)?.text = Html.fromHtml(locationText);
                }

                view.findViewById<ImageView>(R.id.toolbar_share).setOnClickListener {
                    val text =
                        "${job.company.companyName}\n" +
                                "sedang membuka lowongan pekerjaan sebagai '${job.jobPosition}'.\n" +
                                "Lihat informasi selengkapnya ${job.link}"
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TITLE, job.jobPosition)
                        putExtra(Intent.EXTRA_TEXT, text)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, "Bagikan Informasi Pekerjaan")
                    startActivity(shareIntent)
                }

                var bookmarkButton = view.findViewById<ImageView>(R.id.toolbar_bookmark)
                bookmarkButton.setImageResource(if (jobBookmark == true) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark)
                bookmarkButton.setOnClickListener{
                        JobAPI().BookmarkJob(job.jobNo, !jobBookmark, context) {
                            Log.d("Bookmark Response", it.toString())
                            if(it != null) {
                                if (it.code == 210) {
                                    jobBookmark = !jobBookmark
                                    bookmarkButton.setImageResource(if (jobBookmark == true) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark)
                                } else {
                                    Toast.makeText(context, it.Message, Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }

                val btnWithdraw = view.findViewById<MaterialButton>(R.id.btnWithdraw)
                val btnWithdrawBottom = view.findViewById<MaterialButton>(R.id.btnWithdrawBottom)

                view.findViewById<ImageView>(R.id.backButton)?.setOnClickListener{
                    fragmentManager?.popBackStack()
                }
                requireActivity().onBackPressedDispatcher.addCallback(this) {
                    fragmentManager?.popBackStack()
                }

                btnWithdraw.setOnClickListener {
                    withdrawLamaranModal(job.jobNo)
                }
                btnWithdrawBottom.setOnClickListener {
                    withdrawLamaranModal(job.jobNo)
                }

                val lapor = view.findViewById<LinearLayout>(R.id.report_job) as LinearLayout
                lapor.setOnClickListener {
                    val sheet = ReportJob(job.jobNo)
                    activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
                }
            }
        }




    }
    fun withdrawLamaranModal(jobNo : Long){
        val sheet = WithdrawJob(jobNo, id, LamaranPage())
        activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
    }

}
