package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.Marital
import com.ciptakerjaarunika.kerjaloka.model.Data.Resident
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.iMarital
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.iResident
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.residentList
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.iUpdateAdditional

class EditResidentAdapter(val residentNo : Int?, private val listStatus: List<Resident>, val iUpdateAdditional: iUpdateAdditional, val iResident: iResident):
    RecyclerView.Adapter<EditResidentAdapter.ViewHolder>()
{
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var item: TextView
        var container : LinearLayout

        init {
            item = view.findViewById(R.id.item_modal)
            container = view.findViewById(R.id.marital_container)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listStatus[position]
        holder.item.text= currentItem.residentName
        if(currentItem.residentNo == residentNo){
            holder.container.setBackgroundColor(Color.parseColor("#FFDEDE"))
        }
        holder.container.setOnClickListener {
            iUpdateAdditional.updateAdditional(currentItem.residentNo, "resident")
            iResident.close()
        }
    }

    override fun getItemCount(): Int {
        return listStatus.size
    }
}