package com.ciptakerjaarunika.kerjaloka.ui.HomePage
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.AnyChart.pie
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityCompanyDashboardBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomePage.newInstance] factory method to
 * create an instance of this fragment.
 */

private lateinit var binding: ActivityCompanyDashboardBinding

class CompanyDashboard : Fragment(), DatePickerDialog.OnDateSetListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivityCompanyDashboardBinding.inflate(inflater, container, false)
//        val see_all = findViewById(R.id.btn_see_all) as TextView
        val view = inflater.inflate(R.layout.activity_company_dashboard, container, false)
        val btn_search = view.findViewById<LinearLayout>(R.id.btn_search) as LinearLayout
        val btn_notif = view.findViewById<MaterialButton>(R.id.notif_btn) as MaterialButton
        val btn_job = view.findViewById<MaterialCardView>(R.id.btn_pekerjaanComp) as MaterialCardView
        val btn_paket = view.findViewById<MaterialCardView>(R.id.btn_paket) as MaterialCardView
        val btn_tes = view.findViewById<MaterialCardView>(R.id.btn_tes) as MaterialCardView
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
        btn_notif.setOnClickListener {
            // code here to handle intent to notification  activity
            Toast.makeText(activity, "Go to Notification Activity", Toast.LENGTH_SHORT).show()
        }
        btn_job.setOnClickListener {
            // code here to handle intent to job activity
            Toast.makeText(activity, "Go to job Activity", Toast.LENGTH_SHORT).show()
        }
        btn_paket.setOnClickListener {
            // code here to handle intent to company activity
            Toast.makeText(activity, "Paket di Klik!", Toast.LENGTH_SHORT).show()
        }
        val anyChartView2 = view.findViewById(R.id.chart2) as AnyChartView
        APIlib.getInstance().setActiveAnyChartView(anyChartView2);
        val pie2 = AnyChart.column()

        val data2: MutableList<DataEntry> = ArrayList()
        data2.add(ValueDataEntry("Bambang", 10000))
        data2.add(ValueDataEntry("Jake", 12000))
        data2.add(ValueDataEntry("Peter", 18000))
//        pie2.data(data)
        val series2 = pie2.column(data2)
        series2.fill("#FF6666")
        series2.stroke("FF6666")

//        pie2.title("First chart2");

        anyChartView2.setChart(pie2)


        val anyChartView = view.findViewById(R.id.chart1) as AnyChartView
        APIlib.getInstance().setActiveAnyChartView(anyChartView);
        val pie = AnyChart.column()

        val data: MutableList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("John", 10000))
        data.add(ValueDataEntry("Jake", 12000))
        data.add(ValueDataEntry("Peter", 18000))
//        pie.data (data)
        val series = pie.column(data)
        series.fill("#FF6666")
        series.stroke("FF6666")

//        pie.title("");

        anyChartView.setChart(pie)





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
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomePage.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomePage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        TODO("Not yet implemented")
    }
}