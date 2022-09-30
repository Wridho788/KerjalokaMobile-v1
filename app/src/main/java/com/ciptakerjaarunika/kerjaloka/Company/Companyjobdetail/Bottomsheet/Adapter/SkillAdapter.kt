package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.iChooseSkill
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.iUpdatePage2
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.SkillFilter

class SkillAdapter(private var dataset: List<SkillFilter>?, val iChooseSkill: iChooseSkill, val iUpdatePage2: iUpdatePage2): RecyclerView.Adapter<SkillAdapter.ViewHolder?>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtSkill: TextView
        init {
            txtSkill = itemView.findViewById(R.id.txt_location)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_location_job,null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset!![position]
        holder.txtSkill.text = item.skillName
        holder.txtSkill.setOnClickListener {
        iUpdatePage2.updateSkill(item.skillName)
            iChooseSkill.close(item.skillName) }
    }

}
