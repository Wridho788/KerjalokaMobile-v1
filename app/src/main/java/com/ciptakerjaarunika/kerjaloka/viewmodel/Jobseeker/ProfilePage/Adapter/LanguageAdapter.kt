package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter

import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerLanguages
import com.ciptakerjaarunika.kerjaloka.viewmodel.ProfilePage.iEditBahasa

class LanguageAdapter(
    val modeEdit: Boolean,
    private val langList: List<JobseekerLanguages>,
    val iEditBahasa: iEditBahasa?
) : RecyclerView.Adapter<LanguageAdapter.lang>() {

    inner class lang(view: View) : RecyclerView.ViewHolder(view) {

        var bhs: TextView
        var tls: TextView
        var lsn: TextView
        var container: RelativeLayout
        var deleteButton: AppCompatImageButton

        init {
            bhs = view.findViewById(R.id.language)
            tls = view.findViewById(R.id.scoreTulis)
            lsn = view.findViewById(R.id.scoreLisan)
            container = view.findViewById(R.id.card_container)
            deleteButton = view.findViewById(R.id.delete_btn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): lang {
        val view = View.inflate(parent.context, R.layout.card_language_for_modal, null)
        return lang(view)
    }

    override fun onBindViewHolder(holder: lang, position: Int) {
        val currentItem = langList[position]
        if (modeEdit) {
            holder.container.setBackgroundResource(R.drawable.card_background)
            holder.deleteButton.visibility = VISIBLE

            holder.deleteButton.setOnClickListener {
                iEditBahasa?.removeLanguage(currentItem)
            }
        }

        holder.bhs.text = currentItem.languageName
        holder.tls.text = currentItem.languageWrittenScale.toString()
        holder.lsn.text = currentItem.languageSpokenScale.toString()
    }

    override fun getItemCount(): Int {
        return langList.size
    }

}