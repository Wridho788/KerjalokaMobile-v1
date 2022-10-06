package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.iChooseFields
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.iUpdatePage2
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.Field

class FieldsAdapter(
    private var dataset: List<Field>?,
    val iChooseFields: iChooseFields,
    val iUpdatePage2: iUpdatePage2
) : RecyclerView.Adapter<FieldsAdapter.ViewHolder?>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtFields: TextView

        init {
            txtFields = itemView.findViewById(R.id.txt_location)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_location_job, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset!![position]
        holder.txtFields.text = item.fieldName
        holder.txtFields.setOnClickListener {
            iChooseFields.close()
            iUpdatePage2.updateField(item.fieldName, item.fieldNo)
        }
    }

}
