package com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Model.jobList
import com.google.android.material.card.MaterialCardView

class SearchJobAdapter(private val joblist: List<jobList>, private val context: Context) :
    RecyclerView.Adapter<SearchJobAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
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
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val currentItem = joblist[position]
            holder.jobPosition.text = currentItem.jobPosition
//            holder.jobLocation.text = currentItem.location



    }

    override fun getItemCount(): Int {
        return joblist.size
    }
}