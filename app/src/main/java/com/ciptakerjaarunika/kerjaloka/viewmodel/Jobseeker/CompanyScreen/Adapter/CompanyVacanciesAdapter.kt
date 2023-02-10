package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyScreen.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.CompanyBrowseAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyScreen.OnFragmentClickListener
import com.google.android.material.card.MaterialCardView

class CompanyVacanciesAdapter(
    val context: Context?,
    private val companyModel: List<CompanyBrowseAPI.CompanyResponse>,
    private val onFragmentClick: OnFragmentClickListener
) : RecyclerView.Adapter<CompanyVacanciesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var companyName: TextView
        var logo: ImageView
        var field: TextView
        var location: TextView
        var cardCompanyBrowse: MaterialCardView

        init {
            companyName = itemView.findViewById(R.id.followCompanyjobPosition)
            logo = itemView.findViewById(R.id.followCompanyJoblogo)
            field = itemView.findViewById(R.id.followCompanyjobCompany)
            location = itemView.findViewById(R.id.followCompanyjobLocation)
            cardCompanyBrowse = itemView.findViewById(R.id.card_followed_company_job)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_job, null)
        view.layoutParams = ConstraintLayout.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = companyModel[position]
        holder.companyName.text = currentItem.companyName
        holder.field.text = currentItem.field
        holder.location.text = currentItem.location

        Glide.with(holder.itemView.context)
            .load(config().portAddress + "photo/Profile/" + currentItem.logo).fitCenter()
            .into(holder.logo)

        holder.cardCompanyBrowse.setOnClickListener {
            onFragmentClick.onCompanyDetailPage(currentItem.userNo)
        }
    }

    override fun getItemCount(): Int {
        return companyModel.size
    }
}