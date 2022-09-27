package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.app.Activity
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.model.Data.Marital
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.iMarital
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.maritalStatus
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.iUpdateAdditional
import com.giphy.sdk.analytics.GiphyPingbacks.context

class EditMaritalAdapter(val maritalNo : Int?, private val listStatus: List<Marital>,val iUpdateAdditional: iUpdateAdditional, val iMarital: iMarital):
    RecyclerView.Adapter<EditMaritalAdapter.EditMarital>()
{
    inner class EditMarital(view: View): RecyclerView.ViewHolder(view){
        var item: TextView
        var container : LinearLayout

        init {
            item = view.findViewById(R.id.item_modal)
            container = view.findViewById(R.id.marital_container)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditMarital {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return EditMarital(view)
    }

    override fun onBindViewHolder(holder: EditMarital, position: Int) {
        val currentItem = listStatus[position]
        holder.item.text= currentItem.maritalName

        if(currentItem.maritalNo == maritalNo){
            holder.container.setBackgroundColor(Color.parseColor("#FFDEDE"))
        }
        holder.container.setOnClickListener {
            iUpdateAdditional.updateAdditional(currentItem.maritalNo, "marital")
            iMarital.close()
        }
    }

    override fun getItemCount(): Int {
        return listStatus.size
    }
}