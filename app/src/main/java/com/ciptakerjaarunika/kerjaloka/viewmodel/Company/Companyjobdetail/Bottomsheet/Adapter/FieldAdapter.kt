package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet.iUpdateField
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.FieldFilter
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobField

class FieldAdapter(val value: Int?, private val dataList: List<FieldFilter>, val iUpdateField: iUpdateField):
    RecyclerView.Adapter<FieldAdapter.ChooseType>()
{

    inner class ChooseType(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView
        var container : LinearLayout

        init {
            item = view.findViewById(R.id.item_modal)
            container = view.findViewById(R.id.container)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseType {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return ChooseType(view)
    }

    override fun onBindViewHolder(holder: ChooseType, position: Int) {
        val currentItem = dataList[position]
        holder.item.text= currentItem.fieldName

        if(currentItem.fieldNo == value){
            holder.container.setBackgroundColor(Color.parseColor("#FFDEDE"))
        }
        holder.container.setOnClickListener{
            iUpdateField.updateField( JobField(currentItem.fieldName, currentItem.fieldNo, null))
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}