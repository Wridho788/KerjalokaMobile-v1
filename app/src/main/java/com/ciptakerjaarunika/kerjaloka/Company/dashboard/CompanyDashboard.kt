package com.ciptakerjaarunika.kerjaloka.ui.HomePage

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityCompanyDashboardBinding
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.CompanyNotification
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.Notification
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.datepicker.*
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomePage.newInstance] factory method to
 * create an instance of this fragment.
 */


class CompanyDashboard : Fragment(), DatePickerDialog.OnDateSetListener {

    private val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    var label: String?= null
    var startDate: String? =null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding: ActivityCompanyDashboardBinding = ActivityCompanyDashboardBinding.inflate(inflater, container, false)

//        val see_all = findViewById(R.id.btn_see_all) as TextView
        val view = inflater.inflate(R.layout.activity_company_dashboard, container, false)
        val btn_search = view.findViewById<LinearLayout>(R.id.btn_search) as LinearLayout
        val btn_notif = view.findViewById<MaterialButton>(R.id.notif_btn) as MaterialButton
        val btn_job = view.findViewById<MaterialCardView>(R.id.btn_pekerjaanComp) as MaterialCardView
        val btn_paket = view.findViewById<MaterialCardView>(R.id.btn_paket) as MaterialCardView
        val btn_tes = view.findViewById<MaterialCardView>(R.id.btn_tes) as MaterialCardView
        val img_btn_calendar1 = view.findViewById<LinearLayout>(R.id.set_calendar1) as LinearLayout
        val img_btn_calendar2 = view.findViewById<LinearLayout>(R.id.set_calendar2) as LinearLayout
        val plg_tgl2 = view.findViewById<TextView>(R.id.plg_tgl2) as TextView
        val plg_tgl1 = view.findViewById<TextView>(R.id.plg_tgl1) as TextView
        val jlhAppl = view.findViewById<TextView>(R.id.jlhApplicant)
        val jlhAccepted = view.findViewById<TextView>(R.id.jlhAccepted)
        val jlhApplicant = view.findViewById<TextView>(R.id.jlh_applicant)
        val btnSeeApp = view.findViewById<TextView>(R.id.seeApplicant)
        val jlhInterview = view.findViewById<TextView>(R.id.jlhInterview)
        val btnSeeInterview = view.findViewById<TextView>(R.id.seeInterview)
        val jlhFollower = view.findViewById<TextView>(R.id.jlh_org_pengikut)
        val btnSeeFollower = view.findViewById<TextView>(R.id.seeFollower)


        company_profile_api().MyFollowerAmount(context){
            jlhFollower.text = it?.data.toString() + " Orang"
        }

        company_profile_api().InterviewAmount(context){
            jlhInterview.text = it?.data.toString()+ " Orang"
        }

        company_profile_api().MyJob(context){
            var count = 0
            it?.data?.forEach {
                if(it.publish==true){
                    count++
                }
            }
            jlhApplicant.text = count.toString()+" Pekerjaan"
        }



//        var card_test_section =
//            view.findViewById<MaterialCardView>(R.id.card_test) as MaterialCardView
//        val card_interview_section =
//            view.findViewById<MaterialCardView>(R.id.card_interview) as MaterialCardView
//        var btn_see_all_interview =
//            view.findViewById<TextView>(R.id.btn_see_all_interview) as TextView
//        var card_recommendation_job =
//            view.findViewById<MaterialCardView>(R.id.card_recommendation_job) as MaterialCardView
//        var btn_see_all_recommendation_job =
//            view.findViewById<TextView>(R.id.btn_see_all_recommendation_jobs) as TextView
//        var btn_bookmark = view.findViewById<MaterialButton>(R.id.btn_bookmark) as MaterialButton
//        var btn_share = view.findViewById<MaterialButton>(R.id.btn_share) as MaterialButton

        btn_search.setOnClickListener {
            // code here to handle intent to search activity
            Toast.makeText(activity, "Go to Search Activity", Toast.LENGTH_SHORT).show()
        }
        if(SessionManager(context).user != null){
            btn_notif.visibility = View.VISIBLE
            btn_notif.setOnClickListener {
                val intent = Intent(activity, CompanyNotification::class.java)
                startActivity(intent)
            }
        }
        btn_job.setOnClickListener {
            // code here to handle intent to job activity
            Toast.makeText(activity, "Go to job Activity", Toast.LENGTH_SHORT).show()
        }
        btn_paket.setOnClickListener {
            // code here to handle intent to company activity
            Toast.makeText(activity, "Paket di Klik!", Toast.LENGTH_SHORT).show()
        }

        img_btn_calendar1.setOnClickListener {
            val datePickerBuilder: MaterialDatePicker.Builder<Pair<Long, Long>> = MaterialDatePicker
                .Builder
                .dateRangePicker()
                .setTitleText("Select a date")
                .setCalendarConstraints(calendarConstraints())
            val datePicker = datePickerBuilder.build()
            datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER_RANGE")

            datePicker.addOnPositiveButtonClickListener {
                val startDate = sdf.format(it.first)
                val endDate = sdf.format(it.second)
                val msDiff: Long = (it.second - it.first).toLong()
                val daysDiff: Long = TimeUnit.MILLISECONDS.toDays(msDiff)
                company_profile_api().CheckApplicant(startDate, endDate, context){
                    plg_tgl1.setText(startDate + " - " +endDate)
                    jlhAppl.text=it?.data.toString() + " Orang"
                }
            }
        }


        img_btn_calendar2.setOnClickListener {
            val datePickerBuilder: MaterialDatePicker.Builder<Pair<Long, Long>> = MaterialDatePicker
                .Builder
                .dateRangePicker()
                .setTitleText("Select a date")
                .setCalendarConstraints(calendarConstraints())
            val datePicker = datePickerBuilder.build()
            datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER_RANGE")

            datePicker.addOnPositiveButtonClickListener {
                val startDate = sdf.format(it.first)
                val endDate = sdf.format(it.second)
                val msDiff: Long = (it.second - it.first).toLong()
                val daysDiff: Long = TimeUnit.MILLISECONDS.toDays(msDiff)
                plg_tgl2.setText(startDate + " - " +endDate)
                company_profile_api().CheckAccepted(startDate, endDate, context){
                    jlhAccepted.text=it?.data.toString() + " Orang"
                }
            }
        }


//        card_test_section.setOnClickListener {  // code here to handle intent to Selection List activity
//            Toast.makeText(activity, "Seleksi Saya!", Toast.LENGTH_SHORT).show()
//        }
//        card_interview_section.setOnClickListener {
//            // code here to handle intent to Selection Interview activity
//            Toast.makeText(activity, "Interview Saya!", Toast.LENGTH_SHORT).show()
//        }
//        btn_see_all_interview.setOnClickListener {
//            // code here to handle intent to see all activity
//            Toast.makeText(activity, "see all!", Toast.LENGTH_SHORT).show()
//        }
//        card_recommendation_job.setOnClickListener {
//            // code here to handle intent to recommend job activity
//            Toast.makeText(activity, "Pekerjaan Rekomendasi ", Toast.LENGTH_SHORT).show()
//        }
//        btn_see_all_recommendation_job.setOnClickListener {
//            // code here to handle intent to see all activity
//            Toast.makeText(activity, "see all!", Toast.LENGTH_SHORT).show()
//        }
//        btn_bookmark.setOnClickListener {
//            // code here to handle intent to bookmark activity
//            Toast.makeText(activity, "bookmark", Toast.LENGTH_SHORT).show()
//        }
//        btn_share.setOnClickListener {
//            // code here to handle intent to share activity
//            Toast.makeText(activity, "share", Toast.LENGTH_SHORT).show()
//        }

        // Inflate the layout for this fragment
        return view
    }

    private fun calendarConstraints(): CalendarConstraints {
        val min = getLongAsDate(2019,12, 31)
        val dateValidatorMin: CalendarConstraints.DateValidator = DateValidatorPointForward.from(min)
        val dateValidatorMax: CalendarConstraints.DateValidator = DateValidatorPointBackward.now()

        val listValidators = ArrayList<CalendarConstraints.DateValidator>()
        listValidators.apply {
            add(dateValidatorMin)
            add(dateValidatorMax)
        }
        val validators = CompositeDateValidator.allOf(listValidators)

        return CalendarConstraints.Builder()
            .setValidator(validators)
            .build()
    }

    class DayAxisValueFormatter(private val chart: BarLineChartBase<*>) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return "your text $value"
        }
    }

    fun getLongAsDate(year: Int, month: Int, date: Int): Long {
        val calendar: Calendar = GregorianCalendar()
        calendar[Calendar.DAY_OF_MONTH] = date
        calendar[Calendar.MONTH] = month - 1
        calendar[Calendar.YEAR] = year
        return calendar.timeInMillis
    }


    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        TODO("Not yet implemented")
    }
}