package com.ciptakerjaarunika.kerjaloka.viewmodel.HomePage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.fragment_company_jobs
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Package.company_package_list
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityCompanyDashboardBinding
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.InterviewPage.InterviewPage
import com.ciptakerjaarunika.kerjaloka.viewmodel.NotificationPage.CompanyNotification
import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.datepicker.*
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class CompanyDashboard : Fragment(), DatePickerDialog.OnDateSetListener {

    private val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    var label: String? = null
    var startDate: String? = null
    private lateinit var binding: ActivityCompanyDashboardBinding
    private lateinit var hubConnection: HubConnection

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityCompanyDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        val btn_notif = view.findViewById(R.id.notif_btn) as MaterialButton
        val btn_job = view.findViewById<MaterialCardView>(R.id.btn_pekerjaanComp)
        val btn_paket = view.findViewById<MaterialCardView>(R.id.btn_paket)
        val btn_tes = view.findViewById<MaterialCardView>(R.id.btn_tes)
        val img_btn_calendar1 = view.findViewById<LinearLayout>(R.id.set_calendar1)
        val img_btn_calendar2 = view.findViewById<LinearLayout>(R.id.set_calendar2)
        val plg_tgl2 = view.findViewById<TextView>(R.id.plg_tgl2)
        val plg_tgl1 = view.findViewById<TextView>(R.id.plg_tgl1)
        val jlhAppl = view.findViewById<TextView>(R.id.jlhApplicant)
        val jlhAccepted = view.findViewById<TextView>(R.id.jlhAccepted)
        val jlhApplicant = view.findViewById<TextView>(R.id.jlh_applicant)
        val btnSeeApp = view.findViewById<TextView>(R.id.seeApplicant)
        val jlhInterview = view.findViewById<TextView>(R.id.jlhInterview)
        val btnSeeInterview = view.findViewById<TextView>(R.id.seeInterview)
        val jlhFollower = view.findViewById<TextView>(R.id.jlh_org_pengikut)
//        val btnSeeFollower = view.findViewById<TextView>(R.id.seeFollower)

        company_profile_api().MyFollowerAmount(context) {
            if (it != null) {
                jlhFollower.text = it.data.toString() + " Orang"
            } else {
                jlhFollower.text = "0 Orang"
            }
        }

//        company_profile_api().InterviewAmount(context) {
//            if (it != null) {
//                Log.d("total interview", it.toString())
//                jlhInterview.text = it.interview.toString() + " Orang"
//            } else {
//                jlhInterview.text = "0 Orang"
//            }
//        }
        hubConnection = HubConnectionBuilder.create(config().portAddress + "/ws/chat").build()
        if (SessionManager(context).user != null && hubConnection.connectionState != HubConnectionState.CONNECTED) {
            hubConnection.start()
            hubConnection.on(
                "connected",
                { res ->
                    val userNo = SessionManager(context).user!!.userNo.toString()
                    hubConnection.send("Connecting", userNo, SessionManager(context).deviceId)
                }, String::class.java
            )
            hubConnection.on(
                "getmessage", { res: chat_data ->
                    SessionManager(context).chatData = res
                    jlhInterview.text = "${res.sections[0].notRead} Orang"
                },
                chat_data::class.java
            )
        }

        btnSeeInterview.setOnClickListener {
            val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
            ft.addToBackStack("MainActivity")
            ft.replace(id, InterviewPage(), "companyFragment")
            ft.commit()
        }

        company_profile_api().MyJob(context) {
            var count = 0
            Log.d("myjob", it.toString())
            if (it != null) {

                it.data.forEach {
                    if (it.publish == true) {
                        count++
                    }
                }
            }
            jlhApplicant.text = count.toString() + " Pekerjaan"
        }

        btnSeeApp.setOnClickListener {
            val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
            ft.addToBackStack("MainActivity")
            ft.replace(id, fragment_company_jobs(), "companyFragment")
            ft.commit()
        }

        if (SessionManager(context).user != null) {
            btn_notif.visibility = View.VISIBLE
            btn_notif.setOnClickListener {
                val intent = Intent(activity, CompanyNotification::class.java)
                startActivity(intent)
            }
        }
        btn_job.setOnClickListener {
            val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
            ft.addToBackStack("MainActivity")
            ft.replace(id, fragment_company_jobs(), "companyFragment")
            ft.commit()
        }
        btn_paket.setOnClickListener {
            val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
            ft.addToBackStack("MainActivity")
            ft.replace(id, company_package_list(), "companyFragment")
            ft.commit()
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
                company_profile_api().CheckApplicant(startDate, endDate, context) {
                    if (it != null) {
                        plg_tgl1.text = startDate + " - " + endDate
                        jlhAppl.text = it.data.toString() + " Orang"
                    }
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
                plg_tgl2.text = startDate + " - " + endDate
                company_profile_api().CheckAccepted(startDate, endDate, context) {
                    if (it != null) {
                        jlhAccepted.text = it.data.toString() + " Orang"
                    }
                }
            }
        }
        return view
    }

    private fun calendarConstraints(): CalendarConstraints {
        val min = getLongAsDate(2019, 12, 31)
        val dateValidatorMin: CalendarConstraints.DateValidator =
            DateValidatorPointForward.from(min)
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