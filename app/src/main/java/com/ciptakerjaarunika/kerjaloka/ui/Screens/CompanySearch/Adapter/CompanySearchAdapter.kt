package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter

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
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.search_company_model
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.iSearchCompany
import com.google.android.material.card.MaterialCardView

class CompanySearchAdapter(
    private val companyList: List<search_company_model>,
    private val context: Context,
    val iSearchCompany: iSearchCompany
) : RecyclerView.Adapter<CompanySearchAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var companyName: TextView
        var fieldName: TextView
        var locationText: TextView
        var logo: ImageView
        var cardCompany: MaterialCardView

        init {
            companyName = itemView.findViewById(R.id.followCompanyjobPosition)
            fieldName = itemView.findViewById(R.id.followCompanyjobCompany)
            locationText = itemView.findViewById(R.id.followCompanyjobLocation)
            logo = itemView.findViewById(R.id.followCompanyJoblogo)
            cardCompany = itemView.findViewById(R.id.card_followed_company_job)
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
        val currentItem = companyList[position]
        holder.companyName.text = currentItem.companyName
        holder.fieldName.text = currentItem.field
        holder.locationText.text = currentItem.location.city + ", " + currentItem.location.province
        Glide.with(holder.itemView.context)
            .load(config().portAddress + "/photo/Profile/" + currentItem.logo).fitCenter()
            .into(holder.logo)
        holder.cardCompany.setOnClickListener {
            iSearchCompany.onCompanyDetailPage(currentItem.companyNo)
        }
    }

    override fun getItemCount(): Int {
      return companyList.size
    }
}