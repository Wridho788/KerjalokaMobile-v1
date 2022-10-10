package com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Model.jobList
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.onFragmentTransactionList
import com.google.android.material.card.MaterialCardView
import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class SearchJobAdapter(
    private val joblist: List<jobList>,
    private val context: Context,
    private val onFragmentClickListener: onFragmentTransactionList
) :
    RecyclerView.Adapter<SearchJobAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var jobPosition: TextView
        var jobLocation: TextView
        var jobCompany: TextView
        var createOn: TextView
        var logo: ImageView
        var cardJob: MaterialCardView


        init {
            jobPosition = itemView.findViewById(R.id.jobPosition)
            jobLocation = itemView.findViewById(R.id.jobLocation)
            jobCompany = itemView.findViewById(R.id.jobCompany)
            createOn = itemView.findViewById(R.id.createdOn)
            logo = itemView.findViewById(R.id.logo)
            cardJob = itemView.findViewById(R.id.card_recommendation_job)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_recommendation_job, null)
        view.layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            return ViewHolder(view)
    }

    private var inputDate: Date? = null
    private var outputDate: Date? = null
    private var formattedDateString: String? = null
    private var prettyTimeString: String? = null

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = joblist[position]
        holder.jobPosition.text = currentItem.jobPosition
        holder.jobCompany.text = currentItem.companyName
        holder.jobLocation.text = currentItem.jobLocations[0].location
        Glide.with(holder.itemView.context)
            .load(config().portAddress + "/photo/Profile/" + currentItem.photo).fitCenter()
            .into(holder.logo)
        val dateString = currentItem.createdOn
        val convertDate = SimpleDateFormat("yyyy-MM-dd kk:mm:ss")
        val dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa")
        try {
            inputDate = convertDate.parse(dateString)
            formattedDateString = inputDate?.let { it1 -> dateFormat.format(it1) }
            outputDate = formattedDateString?.let { it1 -> dateFormat.parse(it1) }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val prettyTime = PrettyTime()
        prettyTimeString = prettyTime.format(outputDate)
        holder.createOn.text = prettyTimeString
        holder.cardJob.setOnClickListener{
            onFragmentClickListener.onFragmentTransactionListenerClick(currentItem.companyNo, currentItem.jobNo)
        }

    }

    override fun getItemCount(): Int {
        var limit: Int = 4
        return Math.min(joblist.size, limit)
    }
}