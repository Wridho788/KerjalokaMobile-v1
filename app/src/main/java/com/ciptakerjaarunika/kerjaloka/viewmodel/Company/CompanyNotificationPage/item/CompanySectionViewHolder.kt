package com.ciptakerjaarunika.kerjaloka.viewmodel.NotificationPage.item

import android.content.Context
import android.widget.FrameLayout
import android.widget.TextView
import com.ciptakerjaarunika.kerjaloka.R

class CompanySectionViewHolder(
context: Context
): FrameLayout(context) {
    private lateinit var textViewDate: TextView
    private lateinit var readAll: TextView

    init {
        inflate(context, R.layout.view_holder_section, this)
        findView()
    }

    private fun findView(){
        textViewDate = findViewById(R.id.sectionTxt)
        readAll = findViewById(R.id.readAll)
    }

    fun setDate(dateString: String, mark: String){
        textViewDate.text = dateString
        readAll.text = mark
        }
}