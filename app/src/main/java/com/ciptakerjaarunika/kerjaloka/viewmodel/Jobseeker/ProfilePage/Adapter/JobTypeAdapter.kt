package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.JobTypeFilter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Preference.iEditJobType
import kotlin.math.ceil


class JobTypeAdapter(val context: Context, var dataList: List<JobTypeFilter>,val iEditJobType: iEditJobType):
    RecyclerView.Adapter<JobTypeAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var left: TextView
        var right: TextView

        init {
            left = itemView.findViewById(R.id.left_item)
            right = itemView.findViewById(R.id.right_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_job_type, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val leftItem = dataList[position*2]
        holder.left.text = leftItem.jobTypeName
        holder.left.setOnClickListener {
            leftItem.checked = leftItem.checked == false
            iEditJobType.refreshRecyCleview()
        }

        Log.d("Left ", (leftItem.checked == true).toString())
        if(leftItem.checked == true) {
            holder.left.background = ContextCompat.getDrawable(context, R.drawable.card_background_primary_filled)
        }
        else{
            holder.left.background = ContextCompat.getDrawable(context, R.drawable.card_background_primary)
        }
        if((position*2) +1 < dataList.size){
            val rightItem = dataList[(position*2) +1]

            holder.right.text = rightItem.jobTypeName

            holder.right.setOnClickListener {
                rightItem.checked = rightItem.checked == false
                iEditJobType.refreshRecyCleview()
            }

            if(rightItem.checked == true) {
                holder.right.background = ContextCompat.getDrawable(context, R.drawable.card_background_primary_filled)
            }
            else{
                holder.right.background = ContextCompat.getDrawable(context, R.drawable.card_background_primary)
            }
        }

    }

    override fun getItemCount(): Int {
        return ceil((dataList.size/2).toDouble()).toInt()
    }
}