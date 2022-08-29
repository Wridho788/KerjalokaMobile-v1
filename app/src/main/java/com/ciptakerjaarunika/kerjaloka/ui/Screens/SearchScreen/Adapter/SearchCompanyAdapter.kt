package com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Model.companyList
import com.google.android.material.card.MaterialCardView

class SearchCompanyAdapter(private val companyList: List<companyList>, private val context: Context) : RecyclerView.Adapter<SearchCompanyAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var companyName: TextView
        var fieldName: TextView
        var locationText: TextView
//        var logo: ImageView
        var cardCompany: MaterialCardView


        init {
            companyName = itemView.findViewById(R.id.followCompanyjobPosition)
            fieldName = itemView.findViewById(R.id.followCompanyjobCompany)
            locationText = itemView.findViewById(R.id.followCompanyjobLocation)
//            logo = itemView.findViewById(R.id.followCompanyJoblogo)
            cardCompany = itemView.findViewById(R.id.card_followed_company_job)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_job, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return companyList.size
    }
}