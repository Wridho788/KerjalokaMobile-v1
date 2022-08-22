package com.ciptakerjaarunika.kerjaloka.ui.HomePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rJobModel
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.OnFragmentClickListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class RecommendationJobAdapter(
    private val onFragmentClickListener: OnFragmentClickListener
) :
    RecyclerView.Adapter<RecommendationJobAdapter.ViewHolder>() {

    private var list = listOf<rJobModel>(
        rJobModel(
            1,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "satu jam lalu",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        ),
        rJobModel(
            2,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "satu jam lalu",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        ),
        rJobModel(
            3,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "satu jam lalu",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        ),
        rJobModel(
            4,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "satu jam lalu",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        ),
        rJobModel(
            5,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "satu jam lalu",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        ),
    )


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var jobPosition: TextView
        var logo: ImageView
        var jobCompany: TextView
        var jobLocation: TextView
        var timeUploadApplicant: TextView
        var bookmarkedJob: MaterialButton
        var shareableJob: MaterialButton
        var cardRecommendationJob: MaterialCardView


        init {
            jobPosition = itemView.findViewById(R.id.jobPosition)
            logo = itemView.findViewById(R.id.logo)
            jobCompany = itemView.findViewById(R.id.jobCompany)
            jobLocation = itemView.findViewById(R.id.jobLocation)
            timeUploadApplicant = itemView.findViewById(R.id.timeUploadApplicant)
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
        return list.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        holder.jobPosition.text = currentItem.jobPosition
        holder.jobCompany.text = currentItem.jobCompany
        holder.jobLocation.text = currentItem.jobLocation
        holder.timeUploadApplicant.text = currentItem.timeUploadApplicant
        Glide.with(holder.itemView.context).load(currentItem.logo).fitCenter().into(holder.logo)

        holder.bookmarkedJob.setOnClickListener {
            when (currentItem.jobNo) {
                1 -> {
                    Toast.makeText(
                        holder.itemView.context,
                        "Job 1 telah di bookmark",
                        Toast.LENGTH_SHORT
                    ).show()
//                    val intent = Intent(it.context, CityActivity::class.java)
//                    intent.putExtra("currentItem", currentItem)
//                    it.context.startActivity(intent)
                }
                2 -> {
                    Toast.makeText(
                        holder.itemView.context,
                        "Job 2 telah di bookmark",
                        Toast.LENGTH_SHORT
                    ).show()
//                     holder.bookmarkedJob.setBackgroundResource(R.drawable.ic_bookmark_border_black_24dp)
                }
                3 -> {
                    Toast.makeText(
                        holder.itemView.context,
                        "Job 3 telah di bookmark",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                4 -> {
                    Toast.makeText(
                        holder.itemView.context,
                        "Job 4 telah di bookmark",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
        holder.shareableJob.setOnClickListener {
            when (currentItem.jobNo) {
                1 -> {
                    Toast.makeText(
                        holder.itemView.context,
                        "Job 1 telah di share",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                2 -> {
                    Toast.makeText(
                        holder.itemView.context,
                        "Job 2 telah di share",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                3 -> {
                    Toast.makeText(
                        holder.itemView.context,
                        "Job 3 telah di share",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                4 -> {
                    Toast.makeText(
                        holder.itemView.context,
                        "Job 4 telah di share",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        holder.cardRecommendationJob.setOnClickListener {
            onFragmentClickListener.onFragmentClick()
        }
    }

}
