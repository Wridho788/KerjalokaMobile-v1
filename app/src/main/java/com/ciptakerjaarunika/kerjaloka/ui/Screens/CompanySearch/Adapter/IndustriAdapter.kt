package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.industri_model

class IndustriAdapter(private var dataSet: List<*>, context: Context) :
    ArrayAdapter<Any?>(context, R.layout.item_industri, dataSet) {
    private class ViewHolder {
        lateinit var txtFieldname: TextView
        lateinit var checkBox: CheckBox
    }

    override fun getCount(): Int {
        return dataSet.size
    }

    override fun getItem(position: Int): industri_model {
        return dataSet[position] as industri_model
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        val result: View
        if (convertView == null) {
            viewHolder = IndustriAdapter.ViewHolder()
            convertView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_industri, parent, false)
            viewHolder.txtFieldname =
                convertView.findViewById(R.id.txt_fieldname)
            viewHolder.checkBox =
                convertView.findViewById(R.id.checkBox)
            result = convertView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as IndustriAdapter.ViewHolder
            result = convertView
        }
        val item: industri_model = getItem(position)
        viewHolder.txtFieldname.text = item.fieldName
        viewHolder.checkBox.isChecked = item.checked
        return result
    }
}