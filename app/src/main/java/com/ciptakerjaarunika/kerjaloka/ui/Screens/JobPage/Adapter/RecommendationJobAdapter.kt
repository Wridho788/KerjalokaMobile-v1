package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.Adapter

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.Job.RecommendationJob
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rJobModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class RecommendationJobAdapter(val listAuth : List<RecommendationJob>?,val listUnAuth : List<rJobModel>?, val context: Context) : RecyclerView.Adapter<RecommendationJobAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var jobPosition: TextView
        var logo: ImageView
        var jobCompany: TextView
        var jobLocation: TextView
        var createdOn: TextView
        var bookmark_btn : ImageView

        init {
            jobPosition = itemView.findViewById(R.id.jobPosition)
            logo = itemView.findViewById(R.id.logo)
            jobCompany = itemView.findViewById(R.id.jobCompany)
            jobLocation = itemView.findViewById(R.id.jobLocation)
            createdOn = itemView.findViewById(R.id.createdOn)
            bookmark_btn = itemView.findViewById(R.id.btn_bookmark)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_recommendation_job, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(listUnAuth != null){
            val currentItem = listUnAuth?.get(position)
            holder.bookmark_btn.visibility = GONE

            holder.jobPosition.text = currentItem?.jobPosition
            holder.jobCompany.text = currentItem?.companyName
            holder.jobLocation.text = currentItem?.jobLocation
            holder.createdOn.text = currentItem?.createdOn
            Glide.with(holder.itemView.context).load(config().portAddress + "/photo/Profile/" + currentItem?.logo).into(holder.logo)
        }else{
            val currentItem = listAuth?.get(position)
            holder.bookmark_btn.visibility = VISIBLE
            holder.bookmark_btn.setOnClickListener{
                bookmarkJob(currentItem!!.jobNo, currentItem!!.bookmarked, holder)
            }
            holder.jobPosition.text = currentItem?.jobPosition
            holder.jobCompany.text = currentItem?.company!!.companyName
            holder.jobLocation.text = if(currentItem?.jobLocation!!.size >1) "Banyak lokasi" else currentItem.jobLocation[0].location
            holder.createdOn.text = currentItem?.createdOn
            Glide.with(holder.itemView.context).load(config().portAddress + "/photo/Profile/" + currentItem?.company!!.logo).into(holder.logo)
        }
    }
    fun bookmarkJob(jobNo: Long, jobBookmark : Boolean, holder : ViewHolder){
            JobAPI().BookmarkJob(jobNo, !jobBookmark, context) {
                if(it != null) {
                    if (it.code == 210) {
                        holder.bookmark_btn.setImageResource(if (!jobBookmark)  R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark)
                    } else {
                        Toast.makeText(context, it.Message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    override fun getItemCount(): Int {
        return if(listUnAuth != null) listUnAuth.size else listAuth!!.size
    }


}