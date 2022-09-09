package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.size_company_model

class SizeCompanyAdapter(private var dataSet: List<*>, context: Context) :
    ArrayAdapter<Any?>(context, R.layout.item_size_company, dataSet) {
    private class ViewHolder {
        lateinit var txtSizename: TextView
        lateinit var checkBox: CheckBox
    }

    override fun getCount(): Int {
        return dataSet.size
    }

    override fun getItem(position: Int): size_company_model {
        return dataSet[position] as size_company_model
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        val result: View
        if (convertView == null) {
            viewHolder = ViewHolder()
            convertView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_size_company, parent, false)
            viewHolder.txtSizename =
                convertView.findViewById(R.id.txt_sizeName)
            viewHolder.checkBox =
                convertView.findViewById(R.id.checkBox)
            result = convertView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }
        val item: size_company_model = getItem(position)
        viewHolder.txtSizename.text = item.sizeName
        viewHolder.checkBox.isChecked = item.checked
        return result
    }
}