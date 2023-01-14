package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit.GenderModel
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit.iGender
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.manage_profile.iEditBasic

class EditGenderAdapter(val value :Char?, private val genderItems: List<GenderModel>, private val iEditBasic: iEditBasic, private val iGender: iGender):
    RecyclerView.Adapter<EditGenderAdapter.EditGender>()
{

    inner class EditGender(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView
        var container : LinearLayout

        init {
            item = view.findViewById(R.id.item_modal)
            container = view.findViewById(R.id.container)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditGender {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return EditGender(view)
    }

    override fun onBindViewHolder(holder: EditGender, position: Int) {
        val currentItem = genderItems[position]
        holder.item.text= currentItem.Description

        if(currentItem.value == value){
            holder.container.setBackgroundColor(Color.parseColor("#FFDEDE"))
        }
        holder.container.setOnClickListener {
            iEditBasic.updateGender(currentItem.value)
            iGender.close()
        }

    }

    override fun getItemCount(): Int {
        return genderItems.size
    }

}