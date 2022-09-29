package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.Marital
import com.ciptakerjaarunika.kerjaloka.model.Data.Religion
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.ChatAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.iReligion
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.iUpdateAdditional
import okhttp3.internal.notify

class EditReligionAdapter(val religionNo : Int?, private val listStatus: List<Religion>, val iUpdateAdditional: iUpdateAdditional, val iReligion: iReligion):
    RecyclerView.Adapter<EditReligionAdapter.ViewHolder>()
{
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var item: TextView
        var container : LinearLayout

        init {
            item = view.findViewById(R.id.item_modal)
            container = view.findViewById(R.id.container)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listStatus[position]
        holder.item.text= currentItem.religionName
        if(currentItem.religionNo == religionNo){
            holder.container.setBackgroundColor(Color.parseColor("#FFDEDE"))
        }
        holder.container.setOnClickListener {
            iUpdateAdditional.updateAdditional(currentItem.religionNo, "religion")
            iReligion.close()
        }
    }

    override fun getItemCount(): Int {
        return listStatus.size
    }
}