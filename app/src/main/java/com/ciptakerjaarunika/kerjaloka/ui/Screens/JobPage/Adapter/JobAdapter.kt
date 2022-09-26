package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.Adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.Job.RecommendationJob
import com.ciptakerjaarunika.kerjaloka.model.Job.SearchJobModel
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.IJobPage
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class JobAdapter(val ListType : Int,private val rJobList: List<SearchJobModel>, private val context: Context, private val iJobPage: IJobPage) :
    RecyclerView.Adapter<JobAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var jobPosition: TextView
        var logo: ImageView
        var jobCompany: TextView
        var jobLocation: TextView
        var timeUploadApplicant: TextView
        var bookmarkedJob: ImageView
        var shareableJob: MaterialButton
        var cardRecommendationJob: MaterialCardView


        init {
            jobPosition = itemView.findViewById(R.id.jobPosition)
            logo = itemView.findViewById(R.id.logo)
            jobCompany = itemView.findViewById(R.id.jobCompany)
            jobLocation = itemView.findViewById(R.id.jobLocation)
            timeUploadApplicant = itemView.findViewById(R.id.createdOn)
            bookmarkedJob = itemView.findViewById(R.id.btn_bookmark)
            shareableJob = itemView.findViewById(R.id.btn_share)
            cardRecommendationJob = itemView.findViewById(R.id.card_recommendation_job)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_recommendation_job, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rJobList?.size ?: 0
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = rJobList[position]
        holder.jobPosition.text = currentItem.jobPosition
        holder.jobCompany.text = currentItem.company.companyName
        holder.jobLocation.text = if(currentItem?.jobLocation!!.size >1) "Banyak lokasi" else currentItem.jobLocation[0].label
        holder.timeUploadApplicant.text = currentItem.createdOn
        Glide.with(holder.itemView.context).load(config().portAddress + "/photo/Profile/" + currentItem.company.logo).fitCenter().into(holder.logo)
        holder.bookmarkedJob.setImageResource(if (currentItem.bookmarked) R.drawable.ic_bookmark_primary_filled else R.drawable.ic_bookmark_primary)

        holder.bookmarkedJob.setOnClickListener {
                iJobPage.BookmarkJob(ListType, currentItem!!.jobNo.toLong(), position)
        }
        holder.shareableJob.setOnClickListener {
            val text =
                "${currentItem.company.companyName}\n" +
                        "sedang membuka lowongan pekerjaan sebagai '${currentItem.jobPosition}'.\n" +
                        "Lihat informasi selengkapnya ${currentItem.link}"
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TITLE, currentItem.jobPosition)
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }

            context.startActivity(Intent.createChooser(sendIntent, "Bagikan Informasi Pekerjaan"))
        }
        holder.cardRecommendationJob.setOnClickListener {
            iJobPage.GoToJobDetail(currentItem.jobNo.toLong(), if(currentItem.company.userNo != null) currentItem.company.userNo else currentItem.company.companyNo)
        }
    }

}
