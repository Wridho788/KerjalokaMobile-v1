package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.CVbanks.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.Model.applicantModel
import com.google.android.material.card.MaterialCardView

class ApplicantCVBankAdapter(
    private val applicantModel: List<applicantModel>?,
) :
    RecyclerView.Adapter<ApplicantCVBankAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameApplicant: TextView
        var locationApplicant: TextView
        var statusApplicant: TextView
        var profileApplicant: ImageView
        var pinImage: ImageView
        var cardApplicant: MaterialCardView

        init {
            nameApplicant = itemView.findViewById(R.id.name_applicant)
            locationApplicant = itemView.findViewById(R.id.location_applicant)
            statusApplicant = itemView.findViewById(R.id.status_applicant_text)
            profileApplicant = itemView.findViewById(R.id.logo_applicant)
            pinImage = itemView.findViewById(R.id.img_pin)
            cardApplicant = itemView.findViewById(R.id.card_applicant)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_applicant, null)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.setLayoutParams(lp)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = applicantModel!![position]
        holder.nameApplicant.text = currentItem.applicant.name
        holder.locationApplicant.text =
            currentItem.applicant.location.city + ", " + currentItem.applicant.location.province
//        val status = currentItem.publish
//        if (status == true) {
//            holder.statusApplicant.text = "Qualified"
//            holder.statusApplicant.setTextColor(R.color.green_300)
//        } else {
//            holder.statusApplicant.text = "Not Qualified"
//            holder.statusApplicant.setTextColor(Color.RED)
//        }
        Glide.with(holder.itemView.context)
            .load(config().portAddress + "/photo/Profile/" + currentItem.applicant.photo)
            .fitCenter()
            .into(holder.profileApplicant)
//        if (currentItem.bookmarked == true) {
//            holder.pinImage.setImageResource(R.drawable.ic_pin)
//        }


//        holder.cardApplicant.setOnClickListener {
//            onFragmentClickListener?.goToApplicantDetail(currentItem)
//        }
    }

    override fun getItemCount(): Int {
        return applicantModel!!.size
    }
}