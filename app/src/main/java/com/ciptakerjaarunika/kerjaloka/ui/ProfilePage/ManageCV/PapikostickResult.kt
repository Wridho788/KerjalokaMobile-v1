package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.ciptakerjaarunika.kerjaloka.R
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PapikostickResult.newInstance] factory method to
 * create an instance of this fragment.
 */
class PapikostickResult : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_papikostick_result, container, false)

        val score = 1
        val radarchart = view?.findViewById<RadarChart>(R.id.papi_result)
        val radarEntry = ArrayList<RadarEntry>()
        radarEntry.add(RadarEntry(score.toFloat()));
        radarEntry.add(RadarEntry(3F));
        radarEntry.add(RadarEntry(4F));
        radarEntry.add(RadarEntry(2F));
        radarEntry.add(RadarEntry(1F));
        radarEntry.add(RadarEntry(score.toFloat()));
        radarEntry.add(RadarEntry(3F));
        radarEntry.add(RadarEntry(4F));
        radarEntry.add(RadarEntry(2F));
        radarEntry.add(RadarEntry(1F));
        radarEntry.add(RadarEntry(score.toFloat()));
        radarEntry.add(RadarEntry(3F));
        radarEntry.add(RadarEntry(4F));
        radarEntry.add(RadarEntry(2F));
        radarEntry.add(RadarEntry(1F));
        radarEntry.add(RadarEntry(score.toFloat()));
        radarEntry.add(RadarEntry(3F));
        radarEntry.add(RadarEntry(4F));
        radarEntry.add(RadarEntry(2F));
        radarEntry.add(RadarEntry(1F));

        val color = context?.let { ContextCompat.getColor(it, R.color.danger_500) };

        val radarDataSet = RadarDataSet(radarEntry, null)
        radarDataSet.lineWidth = 2f
        radarDataSet.valueTextSize = 14f
        color?.let { radarDataSet.setColor(it) }

        val radarData = RadarData()
        radarData.addDataSet(radarDataSet)

        val label = ArrayList<String>()
        label.add("N")
        label.add("G")
        label.add("A")
        label.add("L")
        label.add("P")
        label.add("I")
        label.add("T")
        label.add("V")
        label.add("X")
        label.add("S")
        label.add("B")
        label.add("O")
        label.add("R")
        label.add("D")
        label.add("C")
        label.add("Z")
        label.add("E")
        label.add("K")
        label.add("F")
        label.add("W")

        val xA = radarchart?.xAxis
        xA?.valueFormatter = IndexAxisValueFormatter(label)
        val xY = radarchart?.yAxis
        xY?.setStartAtZero(true)
        radarchart?.data = radarData

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PapikostickResult.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PapikostickResult().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun setChart() {
        val xvalues = ArrayList<String>()
        xvalues.add("A")
        xvalues.add("B")
        xvalues.add("C")
        xvalues.add("D")
        xvalues.add("E")

    }
}