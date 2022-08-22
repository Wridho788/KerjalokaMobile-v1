package com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.item

import android.content.Context
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.ciptakerjaarunika.kerjaloka.R

class SectionViewHolder(
context: Context
): FrameLayout(context) {
    private lateinit var textViewDate: TextView


    init {
        inflate(context, R.layout.view_holder_section, this)
        findView()
    }

    private fun findView(){
        textViewDate = findViewById(R.id.sectionTxt)
    }

    fun setDate(dateString: String){
        textViewDate.text = dateString
        }
}