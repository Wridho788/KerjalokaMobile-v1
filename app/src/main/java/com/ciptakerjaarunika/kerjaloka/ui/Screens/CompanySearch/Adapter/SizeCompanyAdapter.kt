package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Bottomsheet.iUpdate
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.size_company_model

class SizeCompanyAdapter(
    private var dataSet: List<size_company_model>,
    val context: Context,
    val iUpdate: iUpdate
) : RecyclerView.Adapter<SizeCompanyAdapter.ViewHolder?>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var txtSizename: TextView
        var checkbox: CheckBox
        init {
            txtSizename = itemView.findViewById(R.id.txt_sizeName)
            checkbox = itemView.findViewById(R.id.checkBox_size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_size_company, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataSet[position]
        holder.txtSizename.text = currentItem.sizeName
        holder.checkbox.setOnClickListener{
            dataSet[position].checked = holder.checkbox.isChecked
            val size = dataSet.filter {
                item -> item.checked
            }
            iUpdate.updateSizeCompany(size)
        }
    }
}
//    private class ViewHolder {
//        lateinit var txtSizename: TextView
//        lateinit var checkBox: CheckBox
//    }
//
//    override fun getCount(): Int {
//        return dataSet.size
//    }
//
//    override fun getItem(position: Int): size_company_model {
//        return dataSet[position] as size_company_model
//    }
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        var convertView = convertView
//        val viewHolder: ViewHolder
//        val result: View
//        if (convertView == null) {
//            viewHolder = ViewHolder()
//            convertView =
//                LayoutInflater.from(parent.context)
//                    .inflate(R.layout.item_size_company, parent, false)
//            viewHolder.txtSizename =
//                convertView.findViewById(R.id.txt_sizeName)
//            viewHolder.checkBox =
//                convertView.findViewById(R.id.checkBox)
//            result = convertView
//            convertView.tag = viewHolder
//        } else {
//            viewHolder = convertView.tag as ViewHolder
//            result = convertView
//        }
//        val item: size_company_model = getItem(position)
//        viewHolder.txtSizename.text = item.sizeName
//        viewHolder.checkBox.isChecked = item.checked
//        return result
//    }
