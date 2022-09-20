package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.Adapter

import android.content.Context
import android.content.Intent
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
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.IJobPage
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class RecommendationJobAdapter(val listAuth : List<RecommendationJob>?,val listUnAuth : List<rJobModel>?, val context: Context,private val  iJobPage: IJobPage) : RecyclerView.Adapter<RecommendationJobAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var jobPosition: TextView
        var logo: ImageView
        var jobCompany: TextView
        var jobLocation: TextView
        var createdOn: TextView
        var bookmark_btn : ImageView
        var share_btn : MaterialButton
        var cardRecommendationJob: MaterialCardView

        init {
            jobPosition = itemView.findViewById(R.id.jobPosition)
            logo = itemView.findViewById(R.id.logo)
            jobCompany = itemView.findViewById(R.id.jobCompany)
            jobLocation = itemView.findViewById(R.id.jobLocation)
            createdOn = itemView.findViewById(R.id.createdOn)
            bookmark_btn = itemView.findViewById(R.id.btn_bookmark)
            share_btn = itemView.findViewById(R.id.btn_share)
            cardRecommendationJob = itemView.findViewById(R.id.card_recommendation_job)
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

            holder.share_btn.setOnClickListener {
                val text =
                    "${currentItem?.companyName}\n" +
                            "sedang membuka lowongan pekerjaan sebagai '${currentItem?.jobPosition}'.\n" +
                            "Lihat informasi selengkapnya ${currentItem?.link}"
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TITLE, currentItem?.jobPosition)
                    putExtra(Intent.EXTRA_TEXT, text)
                    type = "text/plain"
                }

                context.startActivity(Intent.createChooser(sendIntent, "Bagikan Informasi Pekerjaan"))
            }

            holder.cardRecommendationJob.setOnClickListener {
                currentItem?.let { it1 -> iJobPage.GoToJobDetail(it1.jobNo, it1.companyNo) }
            }
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
            holder.bookmark_btn.setImageResource(if (currentItem.bookmarked) R.drawable.ic_bookmark_primary_filled else R.drawable.ic_bookmark_primary)

            holder.bookmark_btn.setOnClickListener {
                JobAPI().BookmarkJob(currentItem.jobNo, !currentItem.bookmarked, context) {
                    if(it != null) {
                        if (it.code == 210) {
                            iJobPage.RefreshData()
                        } else {
                            Toast.makeText(context, it.Message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
            holder.share_btn.setOnClickListener {
                val text =
                    "${currentItem?.company.companyName}\n" +
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
                iJobPage.GoToJobDetail(currentItem.jobNo, currentItem.company.companyNo)
            }
        }

    }
    fun bookmarkJob(jobNo: Long, jobBookmark : Boolean, holder : ViewHolder){
            JobAPI().BookmarkJob(jobNo, !jobBookmark, context) {
                if(it != null) {
                    if (it.code == 210) {
                        iJobPage.RefreshData()
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