package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit

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
import com.ciptakerjaarunika.kerjaloka.`interface`.iUpdateTitle
import com.ciptakerjaarunika.kerjaloka.model.Data.Title
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter.ChooseTitleAdapter


class ChooseTitle(val value: Int?, val dataList : List<Title>,val iUpdateTitle: iUpdateTitle) : SuperBottomSheetFragment(),
    iTitle {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ChooseTitleAdapter.chooseTitle>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = View.inflate(context, R.layout.global_modal_edit, null)
        val title = view.findViewById<TextView>(R.id.judul_bottom_sheet)
        title.text = "Pilih Gelar"

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ChooseTitleAdapter(value, dataList,iUpdateTitle, this@ChooseTitle)
        }
    }


    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)
        return (displayMetrics.heightPixels * 0.8).toInt();
    }

    override fun close() {
       this.dismiss()
    }
}

interface iTitle{
    fun close()
}