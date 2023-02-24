package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.Bottomsheet

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class PapikostikResultFragment : SuperBottomSheetFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(
            R.layout.fragment_papikostick_result_company_applicant,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val score = 1
        val papikostick_chart = view.findViewById<RadarChart>(R.id.papi_result_company_applicant)
        val radarEntry = ArrayList<RadarEntry>()
        radarEntry.add(RadarEntry(score.toFloat()))
        radarEntry.add(RadarEntry(3F))
        radarEntry.add(RadarEntry(4F))
        radarEntry.add(RadarEntry(2F))
        radarEntry.add(RadarEntry(1F))
        radarEntry.add(RadarEntry(score.toFloat()))
        radarEntry.add(RadarEntry(3F))
        radarEntry.add(RadarEntry(4F))
        radarEntry.add(RadarEntry(2F))
        radarEntry.add(RadarEntry(1F))
        radarEntry.add(RadarEntry(score.toFloat()))
        radarEntry.add(RadarEntry(3F))
        radarEntry.add(RadarEntry(4F))
        radarEntry.add(RadarEntry(2F))
        radarEntry.add(RadarEntry(1F))
        radarEntry.add(RadarEntry(score.toFloat()))
        radarEntry.add(RadarEntry(3F))
        radarEntry.add(RadarEntry(4F))
        radarEntry.add(RadarEntry(2F))
        radarEntry.add(RadarEntry(1F))

        val color = context?.let { ContextCompat.getColor(it, R.color.danger_500) }

        val radarDataSet = RadarDataSet(radarEntry, null)
        radarDataSet.lineWidth = 2f
        radarDataSet.valueTextSize = 14f
        color?.let { radarDataSet.color = it }

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

        val xA = papikostick_chart?.xAxis
        xA?.valueFormatter = IndexAxisValueFormatter(label)
        val xY = papikostick_chart?.yAxis
        xY?.setStartAtZero(true)
        papikostick_chart?.data = radarData

    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    override fun getCornerRadius() = 20f

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return (displayMetrics.heightPixels * 0.8).toInt()
    }


}