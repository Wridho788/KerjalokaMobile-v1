package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class PapikostickResult : SuperBottomSheetFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_papikostick_result, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ProfileAPI().GetPapiKostick(context){ res->
            if(res?.data != null){
                val radarchart = view?.findViewById<RadarChart>(R.id.papi_result)
                val radarEntry = ArrayList<RadarEntry>()
                val item = res.data
                radarEntry.add(RadarEntry(item.n.toFloat()));
                radarEntry.add(RadarEntry(item.g.toFloat()));
                radarEntry.add(RadarEntry(item.a.toFloat()));
                radarEntry.add(RadarEntry(item.l.toFloat()));
                radarEntry.add(RadarEntry(item.p.toFloat()));
                radarEntry.add(RadarEntry(item.i.toFloat()));
                radarEntry.add(RadarEntry(item.t.toFloat()));
                radarEntry.add(RadarEntry(item.v.toFloat()));
                radarEntry.add(RadarEntry(item.x.toFloat()));
                radarEntry.add(RadarEntry(item.s.toFloat()));
                radarEntry.add(RadarEntry(item.b.toFloat()));
                radarEntry.add(RadarEntry(item.o.toFloat()));
                radarEntry.add(RadarEntry(item.r.toFloat()));
                radarEntry.add(RadarEntry(item.d.toFloat()));
                radarEntry.add(RadarEntry(item.c.toFloat()));
                radarEntry.add(RadarEntry(item.z.toFloat()));
                radarEntry.add(RadarEntry(item.e.toFloat()));
                radarEntry.add(RadarEntry(item.k.toFloat()));
                radarEntry.add(RadarEntry(item.f.toFloat()));
                radarEntry.add(RadarEntry(item.w.toFloat()));

                val color = context?.let { ContextCompat.getColor(context!!, R.color.danger_500) };

                val radarDataSet = RadarDataSet(radarEntry, null)
                radarDataSet.lineWidth = 2f
                radarDataSet.valueTextSize = 14f
                color?.let {color-> radarDataSet.color = color }

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

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight() = ViewGroup.LayoutParams.WRAP_CONTENT
}