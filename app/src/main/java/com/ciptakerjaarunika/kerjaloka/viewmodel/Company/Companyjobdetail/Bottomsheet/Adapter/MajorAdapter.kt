package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.Title
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet.iChooseMajor

class MajorAdapter(
    private var dataset: List<Title>?,
    val iChooseMajor: iChooseMajor,
) : RecyclerView.Adapter<MajorAdapter.ViewHolder?>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtJobType: TextView
        val checkBox: CheckBox

        init {
            txtJobType = itemView.findViewById(R.id.txt_location)
            checkBox = itemView.findViewById(R.id.check_location)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_location, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset!![position]
        holder.txtJobType.text = item.titleName
        holder.checkBox.setOnClickListener {
            dataset!![position].checked = holder.checkBox.isChecked
            val titles = dataset!!.filter { item -> item.checked == true }
//            iChooseMajor.close(titles)
            iChooseMajor.close()
        }
    }

}
