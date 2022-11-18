package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.iUpdateTitle
import com.ciptakerjaarunika.kerjaloka.model.Data.Title
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.iTitle

class ChooseTitleAdapter(val value: Int?, private val titleList: List<Title>, val iUpdateTitle: iUpdateTitle,val iTitle : iTitle):
    RecyclerView.Adapter<ChooseTitleAdapter.chooseTitle>()
{

    inner class chooseTitle(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView
        var container : LinearLayout

        init {
            item = view.findViewById(R.id.item_modal)
            container = view.findViewById(R.id.container)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chooseTitle {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = lp
        return chooseTitle(view)
    }

    override fun onBindViewHolder(holder: chooseTitle, position: Int) {
        val currentItem = titleList[position]
        holder.item.text= currentItem.titleName

        if(currentItem.titleNo == value){
            holder.container.setBackgroundColor(Color.parseColor("#FFDEDE"))
            holder.container.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        holder.container.setOnClickListener {
            iUpdateTitle.updateTitle(currentItem.titleNo)
            iTitle.close()
        }
    }

    override fun getItemCount(): Int {
        return titleList.size
    }

}