package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerRecord
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Record.AppealRecordModal
import com.google.android.material.button.MaterialButton

class RecordAdapter (private val recordList: List<JobseekerRecord>?,val activity: FragmentActivity):
    RecyclerView.Adapter<RecordAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var creator: TextView
        var recordTime: TextView
        var Desc: TextView
        var appealBtn : MaterialButton
        init {
            creator = itemView.findViewById(R.id.record_page_by)
            recordTime = itemView.findViewById(R.id.record_page_date)
            Desc = itemView.findViewById(R.id.recordDesc)
            appealBtn = itemView.findViewById(R.id.btn_appeal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.section_my_record_page, null)
        view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = recordList?.get(position)
        holder.creator.text = currentItem?.ownerName
        holder.recordTime.text = currentItem?.statusChangeOn
        holder.Desc.text = currentItem?.description

        holder.appealBtn.setOnClickListener {
            currentItem?.recordNo?.let { it1 -> AppealRecordModal(it1) }
                ?.show(activity.supportFragmentManager, "location")
        }
    }

    override fun getItemCount(): Int {
        return recordList?.size ?:0
    }

}