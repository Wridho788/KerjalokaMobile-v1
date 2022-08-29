package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetailScreen.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetailScreen.Model.relatedOtherCompanyJobModel
import com.google.android.material.card.MaterialCardView

class RelatedOtherCompanyJobAdapter() :
    RecyclerView.Adapter<RelatedOtherCompanyJobAdapter.ViewHolder>() {
    private var listItem = listOf<relatedOtherCompanyJobModel>(
        relatedOtherCompanyJobModel(
            1,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "satu jam lalu",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        ),
        relatedOtherCompanyJobModel(
            2,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "satu jam lalu",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        ),
        relatedOtherCompanyJobModel(
            3,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "satu jam lalu",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        ),
        relatedOtherCompanyJobModel(
            4,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "satu jam lalu",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        ),
        relatedOtherCompanyJobModel(
            5,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "satu jam lalu",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        ),
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var relatedjobPosition: TextView
        var relatedlogo: ImageView
        var relatedjobCompany: TextView
        var relatedjobLocation: TextView
        var relatedJobDate: TextView
        var cardrelatedJob: MaterialCardView

        init {
            relatedjobPosition = itemView.findViewById(R.id.relatedjobPosition)
            relatedlogo = itemView.findViewById(R.id.relatedJoblogo)
            relatedjobCompany = itemView.findViewById(R.id.relatedjobCompany)
            relatedjobLocation = itemView.findViewById(R.id.relatedjobLocation)
            relatedJobDate = itemView.findViewById(R.id.relatedJobDate)
            cardrelatedJob = itemView.findViewById(R.id.card_related_job)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_job_related, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listItem[position]
        holder.relatedjobPosition.text = currentItem.jobPosition
        holder.relatedjobCompany.text = currentItem.jobCompany
        holder.relatedjobLocation.text = currentItem.jobLocation
        holder.relatedJobDate.text = currentItem.timeUploadApplicant
        Glide.with(holder.itemView.context).load(currentItem.logo).into(holder.relatedlogo)

//        holder.cardrelatedJob.setOnClickListener {
//            onFragmentClickListener.onFragmentClick()
//        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}