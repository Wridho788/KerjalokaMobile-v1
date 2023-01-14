package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.SearchScreen.Adapter

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
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.SearchScreen.Model.companyList
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.SearchScreen.onFragmentTransactionListCompany
import com.google.android.material.card.MaterialCardView

class SearchCompanyAdapter(
    private val companyList: List<companyList>,
    private val context: Context,
    private val onFragmentTransactionListCompany: onFragmentTransactionListCompany
) : RecyclerView.Adapter<SearchCompanyAdapter.ViewHolder>() {
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = companyList[position]
        holder.companyName.text = currentItem.companyName
        holder.fieldName.text = currentItem.fieldName
        holder.locationText.text = currentItem.locationText
        holder.cardCompany.setOnClickListener { onFragmentTransactionListCompany.onFragmentCompanyDetailsClick(currentItem.companyNo)
        }
        Glide.with(holder.itemView.context)
            .load(config().portAddress + "/photo/Profile/" + currentItem.logo).fitCenter()
            .into(holder.logo)
    }

    override fun getItemCount(): Int {
        var limit: Int = 4
        return Math.min(companyList.size, limit)
    }
}