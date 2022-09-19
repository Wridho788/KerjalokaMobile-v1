package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerLanguages

class LanguageAdapter(private val langList: List<JobseekerLanguages>):
    RecyclerView.Adapter<LanguageAdapter.lang>()
{

    inner class lang(view: View) : RecyclerView.ViewHolder(view) {

        var bhs: TextView
        var tls: TextView
        var lsn: TextView

        init {
            bhs = view.findViewById<TextView>(R.id.language)
            tls = view.findViewById<TextView>(R.id.scoreTulis)
            lsn = view.findViewById<TextView>(R.id.scoreLisan)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): lang {
        val view = View.inflate(parent.context, R.layout.card_language_main, null)
        return lang(view)
    }

    override fun onBindViewHolder(holder: lang, position: Int) {
        val currentItem = langList[position]
        holder.bhs.text= currentItem.languageName
        holder.tls.text= currentItem.languageWrittenScale.toString()
        holder.lsn.text= currentItem.languageSpokenScale.toString()
    }

    override fun getItemCount(): Int {
        return langList.size
    }

}