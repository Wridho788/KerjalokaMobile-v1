package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EduAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.ExpAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.LanguageAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.RecordAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.education
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.experience
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.languageList
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.record

class fragment_my_record_page : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterRec: RecyclerView.Adapter<RecordAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_record_page, container, false)

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val RecordList = ArrayList<record>()
        val rec1 = record(
            description="Ini manusia atau bukan? Gayanya kek alien anjink",
        ownerName="TESTING",
        recordNo=1,
        statusChangeOn="2022-06-25T09:03:53"
        )

        RecordList.add(rec1)

        val recyclerViewLang = view.findViewById<RecyclerView>(R.id.recycleRec)
        layoutManager = LinearLayoutManager(activity)
        recyclerViewLang.layoutManager = layoutManager
        adapterRec = RecordAdapter(RecordList)
        recyclerViewLang.adapter = adapterRec
    }


    companion object {
    }
}