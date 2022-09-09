package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.minat_model
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.record
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.review

class ReviewAdapter (private val reviewList: List<review>):
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var creator: TextView
        var recordTime: TextView
        var Desc: TextView

        init {
            creator = itemView.findViewById(R.id.record_page_by)
            recordTime = itemView.findViewById(R.id.record_page_date)
            Desc = itemView.findViewById(R.id.recordDesc)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.section_my_record_page, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = reviewList[position]
        holder.creator.text = currentItem.userFullName
        holder.recordTime.text = currentItem.ratingAt
        holder.Desc.text = currentItem.conRating[0].con
    }

    override fun getItemCount(): Int {
        return reviewList?.size ?:0
    }

}