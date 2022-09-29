package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.Language
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.iChooseLanguage
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.language
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.iEditBahasa

class ChooseLanguageAdapter(val languageNo : Int?, private val langList: List<Language>,val iEditBahasa: iEditBahasa,val iChooseLanguage: iChooseLanguage):
    RecyclerView.Adapter<ChooseLanguageAdapter.chooseLang>()
{
    inner class chooseLang(view: View): RecyclerView.ViewHolder(view){
        var item: TextView
        var container : LinearLayout

        init {
            item = view.findViewById(R.id.item_modal)
            container = view.findViewById(R.id.container)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chooseLang {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return chooseLang(view)
    }

    override fun onBindViewHolder(holder: chooseLang, position: Int) {
        val currentItem = langList[position]
        holder.item.text= currentItem.languageName

        if(currentItem.languageNo == languageNo){
            holder.container.setBackgroundColor(Color.parseColor("#FFDEDE"))
        }
        holder.container.setOnClickListener {
            iEditBahasa.updateLanguage(currentItem)
            iChooseLanguage.close()
        }
    }

    override fun getItemCount(): Int {
        return langList.size
    }

}
