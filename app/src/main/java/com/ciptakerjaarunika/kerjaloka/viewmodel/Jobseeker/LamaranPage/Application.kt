package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.LamaranPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.Job.ApplicationData

class Application(
    private val data: List<ApplicationData>,
    private val context: Context,
    private val cellClickListener: LamaranPage
) : RecyclerView.Adapter<Application.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val Jobposition: TextView
        val CompanyName: TextView
        val CompanyAddress: TextView
        val TotalTest: TextView
        val TestTaken: TextView
        val CompanyLogo: ImageView
        val TestContainer: LinearLayout
//            val Requirment: TextView

        init {
            Jobposition = view.findViewById(R.id.card_title)
            CompanyName = view.findViewById(R.id.card_companyname)
            CompanyAddress = view.findViewById(R.id.card_location)
            TotalTest = view.findViewById(R.id.card_totalTest)
            TestTaken = view.findViewById(R.id.card_testHasTake)
            CompanyLogo = view.findViewById(R.id.img_company_logo)
            TestContainer = view.findViewById(R.id.test_container)
//                Requirment = view.findViewById(R.id.ca)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.myapplicant_card, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(config().portAddress + "photo/Profile/" + data[position].job.company.logo)
            .into(viewHolder.CompanyLogo)
        viewHolder.Jobposition.text = data[position].job.jobPosition
        viewHolder.CompanyName.text = data[position].job.company.companyName
        viewHolder.CompanyAddress.text =
            "${data[position].job.company.location.city}, ${data[position].job.company.location.province}"
        if (data[position].tests != null) {
            viewHolder.TestTaken.text = data[position].tests.filter { item -> item.testResult != null }.size.toString()
        } else viewHolder.TestTaken.text = ""
        viewHolder.TotalTest.text = data[position].tests.size.toString()
        if (data[position].tests.isEmpty()) {
            viewHolder.TestContainer.visibility = GONE
        }
//            viewHolder.Requirment.text= data[position].requirentment
        viewHolder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(
                data[position].job.jobNo,
                data[position].job.company.companyNo,
                data[position]
            )
        }
    }

    override fun getItemCount() = data.size
}