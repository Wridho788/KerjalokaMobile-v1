package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.Model.companyModel
import com.google.android.material.card.MaterialCardView

class CompanyFollowedAdapter(private val companyModel: List<companyModel>) : RecyclerView.Adapter<CompanyFollowedAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var jobPosition: TextView
        var logo: ImageView
        var jobCompany: TextView
        var jobLocation: TextView
        var cardCompanyFollower: MaterialCardView

        init {
            jobPosition = itemView.findViewById(R.id.followCompanyjobPosition)
            logo = itemView.findViewById(R.id.followCompanyJoblogo)
            jobCompany = itemView.findViewById(R.id.followCompanyjobCompany)
            jobLocation = itemView.findViewById(R.id.followCompanyjobLocation)
            cardCompanyFollower = itemView.findViewById(R.id.card_followed_company_job)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = View.inflate(parent.context, R.layout.item_card_job, null)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = companyModel[position]
        holder.jobPosition.text = currentItem.jobPosition
        holder.jobCompany.text = currentItem.jobCompany
        holder.jobLocation.text = currentItem.jobLocation
        Glide.with(holder.itemView.context).load(currentItem.logo).fitCenter().into(holder.logo)

        holder.cardCompanyFollower.setOnClickListener{
            when (currentItem.jobNo) {
                1 -> {
                    Toast.makeText(
                        holder.itemView.context,
                        "Job 1 telah di klik",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                2 -> {
                    Toast.makeText(
                        holder.itemView.context,
                        "Job 2 telah di klik",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                3 -> {
                    Toast.makeText(
                        holder.itemView.context,
                        "Job 3 telah di klik",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                4 -> {
                    Toast.makeText(
                        holder.itemView.context,
                        "Job 4 telah di klik",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                5 -> {
                    Toast.makeText(
                        holder.itemView.context,
                        "Job 5 telah di klik",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    override fun getItemCount(): Int {
       return companyModel.size
    }
}