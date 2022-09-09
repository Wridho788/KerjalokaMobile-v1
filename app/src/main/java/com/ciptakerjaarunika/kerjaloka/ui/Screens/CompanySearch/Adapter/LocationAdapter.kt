package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Bottomsheet.iUpdate
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.location_model

class LocationAdapter(private var dataSet: List<location_model>, val context: Context, val iUpdate: iUpdate) :
    RecyclerView.Adapter<LocationAdapter.ViewHolder?>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtLocation: TextView
        var checkBox: CheckBox

        init {
            txtLocation = itemView.findViewById(R.id.txt_location)
            checkBox = itemView.findViewById(R.id.check_location)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_location, null)
        val checked = view.findViewById<CheckBox>(R.id.check_location)


        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataSet[position]
        holder.txtLocation.text = currentItem.city

        holder.checkBox.setOnClickListener{
            dataSet[position].checked = holder.checkBox.isChecked

            val locations = dataSet.filter { item->
                item.checked
            }
            iUpdate.updateLocation(locations)
        }
    }



    //    private class ViewHolder {
//        lateinit var txtName: TextView
//        lateinit var checkBox: CheckBox
//    }

//    override fun getCount(): Int {
//        return dataSet.size
//    }

//    override fun getItem(position: Int): location_model {
//        return dataSet[position] as location_model
//    }

//    @SuppressLint("ViewHolder")
//    override fun getView(
//        position: Int,
//        convertView: View?,
//        parent: ViewGroup
//    ): View {
//        var convertView = convertView
//        val viewHolder: ViewHolder
//        val result: View
//
//        convertView = LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
//
//        if (convertView == null) {
//            viewHolder = ViewHolder()
//            viewHolder.txtName =
//                convertView.findViewById(R.id.txt_location)
//            viewHolder.checkBox =
//                convertView.findViewById(R.id.checkBox)
//            result = convertView
//            convertView.tag = viewHolder
//        } else {
//            viewHolder = convertView.tag as ViewHolder
//            result = convertView
//        }
//        val item: location_model = getItem(position)
//
//        viewHolder.txtName.text = item.city
//        viewHolder.checkBox.isChecked = item.checked
//        var check = viewHolder.checkBox
//        if (check.isChecked() == true) {
//            Log.d("ceklis", check.toString())
//
//        }
//        return result
//    }


}


