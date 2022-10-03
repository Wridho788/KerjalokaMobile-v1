package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.model.Profile.PapiKostickResult
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate


class PapikostickResult(val item: PapiKostickResult) : SuperBottomSheetFragment() {

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

        var radarchart = view?.findViewById<RadarChart>(R.id.papi_result)

            if(item != null){
                view?.findViewById<TextView>(R.id.Nscore)?.text = item.n.toString()
                view?.findViewById<TextView>(R.id.Gscore)?.text = item.g.toString()
                view?.findViewById<TextView>(R.id.Ascore)?.text = item.a.toString()
                view?.findViewById<TextView>(R.id.Lscore)?.text = item.l.toString()
                view?.findViewById<TextView>(R.id.Pscore)?.text = item.p.toString()
                view?.findViewById<TextView>(R.id.Iscore)?.text = item.i.toString()
                view?.findViewById<TextView>(R.id.Tscore)?.text = item.t.toString()
                view?.findViewById<TextView>(R.id.Vscore)?.text = item.v.toString()
                view?.findViewById<TextView>(R.id.Xscore)?.text = item.x.toString()
                view?.findViewById<TextView>(R.id.Sscore)?.text = item.s.toString()
                view?.findViewById<TextView>(R.id.Bscore)?.text = item.b.toString()
                view?.findViewById<TextView>(R.id.Oscore)?.text = item.o.toString()
                view?.findViewById<TextView>(R.id.Rscore)?.text = item.r.toString()
                view?.findViewById<TextView>(R.id.Dscore)?.text = item.d.toString()
                view?.findViewById<TextView>(R.id.Cscore)?.text = item.c.toString()
                view?.findViewById<TextView>(R.id.Zscore)?.text = item.z.toString()
                view?.findViewById<TextView>(R.id.Escore)?.text = item.e.toString()
                view?.findViewById<TextView>(R.id.Kscore)?.text = item.k.toString()
                view?.findViewById<TextView>(R.id.Fscore)?.text = item.f.toString()
                view?.findViewById<TextView>(R.id.Wscore)?.text = item.w.toString()

                val radarEntry = ArrayList<RadarEntry>()

                radarEntry.add(RadarEntry(item.n.toFloat(), ));
                radarEntry.add(RadarEntry(item.g.toFloat(), ));
                radarEntry.add(RadarEntry(item.a.toFloat(), ));
                radarEntry.add(RadarEntry(item.l.toFloat(), ));
                radarEntry.add(RadarEntry(item.p.toFloat(), ));
                radarEntry.add(RadarEntry(item.i.toFloat(), ));
                radarEntry.add(RadarEntry(item.t.toFloat(), ));
                radarEntry.add(RadarEntry(item.v.toFloat(), ));
                radarEntry.add(RadarEntry(item.x.toFloat(), ));
                radarEntry.add(RadarEntry(item.s.toFloat(), ));
                radarEntry.add(RadarEntry(item.b.toFloat(), ));
                radarEntry.add(RadarEntry(item.o.toFloat(), ));
                radarEntry.add(RadarEntry(item.r.toFloat(), ));
                radarEntry.add(RadarEntry(item.d.toFloat(), ));
                radarEntry.add(RadarEntry(item.c.toFloat(), ));
                radarEntry.add(RadarEntry(item.z.toFloat(), ));
                radarEntry.add(RadarEntry(item.e.toFloat(), ));
                radarEntry.add(RadarEntry(item.k.toFloat(), ));
                radarEntry.add(RadarEntry(item.f.toFloat(), ));
                radarEntry.add(RadarEntry(item.w.toFloat(), ));

                val color = context?.let { ContextCompat.getColor(context!!, R.color.danger_500) };

                val radarDataSet = RadarDataSet(radarEntry, "Hasil Test Papi Kostick")
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