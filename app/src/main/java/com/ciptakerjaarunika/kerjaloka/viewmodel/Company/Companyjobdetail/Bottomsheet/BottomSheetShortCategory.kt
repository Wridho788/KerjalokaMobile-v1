package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.short_question_category
import com.ciptakerjaarunika.kerjaloka.model.Data.ShortQuestionCategory
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet.Adapter.CategoryShortQuestionAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.ManageJobPage.iUpdatePage5

class BottomSheetShortCategory(val iUpdatePage5: iUpdatePage5) : SuperBottomSheetFragment(),
    iChooseCategory {
    private var short_question_list: List<ShortQuestionCategory>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.layout_bottomsheet_edit_job, container, false)

        view.layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

        val title = view.findViewById<TextView>(R.id.title_location)
        val rv_category = view.findViewById<RecyclerView>(R.id.list_location_view)
        title.text = "Kategori Pertanyaan Pendek"
        short_question_category().getShortQuestionCategory(context) {
            if (it != null) {
                short_question_list = it.data
                rv_category.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = CategoryShortQuestionAdapter(
                        short_question_list,
                        this@BottomSheetShortCategory, iUpdatePage5
                    )
                }
            }
        }
        return view
    }

    override fun getCornerRadius() = 20f

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)
        return (displayMetrics.heightPixels * 0.8).toInt()
    }

    override fun close() {
        this.dismiss()
    }
}

interface iChooseCategory {
    fun close()
}