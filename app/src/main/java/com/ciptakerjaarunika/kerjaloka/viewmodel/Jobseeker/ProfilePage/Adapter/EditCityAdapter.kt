package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit.iCity
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.manage_profile.iEditBasic

class EditCityAdapter(private val cityNo: Int?, private val locations: List<LocationFilter>, private val iEditBasic: iEditBasic, private val iCity: iCity):
    RecyclerView.Adapter<EditCityAdapter.EditCity>()
{
    inner class EditCity(view: View): RecyclerView.ViewHolder(view){
        var item: TextView
        var container : LinearLayout

        init {
            item = view.findViewById(R.id.item_modal)
            container = view.findViewById(R.id.container)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditCity {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return EditCity(view)
    }

    override fun onBindViewHolder(holder: EditCity, position: Int) {
        val currentItem = locations[position]
        holder.item.text= "${currentItem.city}, ${currentItem.province}"

        if(currentItem.locationsNo == cityNo){
            holder.container.setBackgroundColor(Color.parseColor("#FFDEDE"))
        }
        holder.container.setOnClickListener {
            iEditBasic.updateCity(currentItem.locationsNo)
            iCity.close()
        }
    }

    override fun getItemCount(): Int {
        return locations.size
    }
}